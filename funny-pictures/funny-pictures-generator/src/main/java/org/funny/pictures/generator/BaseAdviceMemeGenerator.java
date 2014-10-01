package org.funny.pictures.generator;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.annotation.Resource;

import org.funny.pictures.generator.api.AdviceMemeContext;
import org.funny.pictures.generator.api.AdviceMemeGenerator;
import org.funny.pictures.generator.api.GeneratorException;
import org.funny.pictures.generator.api.ImageHandle;
import org.funny.pictures.generator.util.ImageInformationExtractor;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.model.content.MimeType;

/**
 * Generator of advice comics.
 * @author odisseus
 *
 */
public class BaseAdviceMemeGenerator implements AdviceMemeGenerator {

	private static final Logger LOG = LoggerFactory.getLogger(BaseAdviceMemeGenerator.class);
	
	private float captionHeightRatio = 0.2f;

	public static final MimeType DEFAULT_OUTPUT_FORMAT = MimeType.IMAGE_PNG_PNG;

	private final MimeType internalFormat = MimeType.IMAGE_BMP_BMP;

	private MimeType outputFormat;

	private ConvertCmd convertCommand = new ConvertCmd();

	private CompositeCmd compositeCommand = new CompositeCmd();
	
	@Resource(name = "imageInformationExtractor")
	private ImageInformationExtractor imageInfoExtractor;

	public final ImageInformationExtractor getImageInfoExtractor() {
		return imageInfoExtractor;
	}

	//TODO check whether this setter is necessary
	public final void setImageInfoExtractor(
			final ImageInformationExtractor imageInfoExtractor) {
		this.imageInfoExtractor = imageInfoExtractor;
	}

	@Override
	public ImageHandle generate(final AdviceMemeContext context) {

		LOG.debug("Generating an advice meme from context: " + context.toString());

		Path templateInput = null;
		Path topCaption = null;
		Path bottomCaption = null;
		Path result = null;
		try {			
			
			templateInput = toTempFile(context.getTemplateInputStream(), context.getMimeType().getExtension());
			result = prepareTemplate(templateInput);
			
			Dimension originalDimension = imageInfoExtractor.getImageDimension(result);
			int captionHeight =  (int) (captionHeightRatio * originalDimension.height);
			
			topCaption = generateCaption(context.getTopCaption(), originalDimension.width, captionHeight);
			bottomCaption = generateCaption(context.getBottomCaption(), originalDimension.width, captionHeight);

			superimpose(result, topCaption, originalDimension, 0, 0);
			superimpose(result, bottomCaption, originalDimension, 0, originalDimension.height - captionHeight);

			InputStream resultStream = new FileInputStream(result.toString());
			LOG.debug("Advice meme generated");

			return new ImageHandle(resultStream, outputFormat);

		} catch (IOException | IM4JavaException | InterruptedException e) {
			LOG.error("Internal generation procedure failed", e);
			throw new GeneratorException(e.getMessage(), e);
		} finally {
			try {
				LOG.debug("Deleting temporary files");
				if (templateInput != null) {
					Files.deleteIfExists(templateInput);
				}
				if (topCaption != null) {
					Files.deleteIfExists(topCaption);
				}
				if (bottomCaption != null) {
					Files.deleteIfExists(bottomCaption);
				}
				LOG.debug("Temporary files deleted");
			} catch (Exception e) {
				LOG.error("Error file file delete", e);
			}
		}
	}

	/**
	 * Renders an intermediate image with caption.
	 * Tries to use the font size that fits best into the specified dimensions.
	 * @param text caption text
	 * @param width width of caption area
	 * @param height height of caption area
	 * @return path to generated image
	 * @throws IOException when an IO problem occurs
	 * @throws InterruptedException when thread is interrupted
	 * @throws IM4JavaException when an im4java problem occurs
	 */
	private Path generateCaption(final String text, final int width, final int height) throws IOException, InterruptedException, IM4JavaException {
		Path result = Files.createTempFile("caption", internalFormat.getExtension());
		IMOperation op = new IMOperation();
		op.size(width, height);
		op.background("none");
		op.fill("white");
		op.stroke("black");
		op.strokewidth((int) Math.ceil(((double) height) / 40));
		op.font("Impact-Regular");
		op.gravity("Center");
		op.addImage("label:" + text);
		op.addImage();

		Object[] args = new String[] {result.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

		convertCommand.run(op, args);

		return result;
	}

	/**
	 * Creates a copy of template image to work with,
	 * converted to internal format.
	 * @param templatePath path to template image
	 * @return path to converted working copy
	 * @throws IOException when an IO problem occurs
	 * @throws InterruptedException when thread is interrupted
	 * @throws IM4JavaException when an im4java problem occurs
	 */
	private Path prepareTemplate(final Path templatePath) throws IOException, InterruptedException, IM4JavaException {
		Path result = Files.createTempFile("template", internalFormat.getExtension());

		IMOperation op = new IMOperation();
		op.addImage();
		op.addImage();

		Object[] args = new String[] {templatePath.toString(), result.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);
		convertCommand.run(op, args);

		return result;
	}

	/**
	 * Superimposes one image on top of another, with specified
	 * size of resulting image and offset of top layer.
	 * @param bottom bottom layer
	 * @param top top layer
	 * @param resultDimension dimension of the resulting image; required for IM backend
	 * @param offsetX horizontal offset of top layer
	 * @param offsetY vertical offset of top layer
	 * @throws IOException when an IO problem occurs
	 * @throws InterruptedException when thread is interrupted
	 * @throws IM4JavaException when an im4java problem occurs
	 */
	private void superimpose(final Path bottom, final Path top, final Dimension resultDimension, final int offsetX, final int offsetY) 
			throws IOException, InterruptedException, IM4JavaException {

		IMOperation op = new IMOperation();
		op.geometry(resultDimension.width, resultDimension.height, offsetX, offsetY);
		op.addImage();
		op.addImage();
		op.addImage();

		Object[] args = new String[] {top.toString(), bottom.toString(), bottom.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);
		compositeCommand.run(op, args);
	}

	/**
	 * Writes data from an input stream to temporary file.
	 * @param istream input stream
	 * @param extensionSuffix extension of file to generate
	 * @return path to generated file
	 * @throws IOException when an IO problem occurs
	 */
	private Path toTempFile(final InputStream istream, final String extensionSuffix) throws IOException {
		Path result = Files.createTempFile("template", extensionSuffix);
		Files.copy(istream, result, StandardCopyOption.REPLACE_EXISTING);
		LOG.debug("Created a temporary file: " + result.toAbsolutePath().toString());
		return result;
	}

	/**
	 * Returns MIME type of output images.
	 * @return MIME type of output images
	 */
	public MimeType getOutputFormat() {
		return outputFormat;
	}

	/**
	 * Sets MIME type of output images.
	 * @param outputFormat MIME type of output images
	 */
	public void setOutputFormat(final MimeType outputFormat) {
		this.outputFormat = outputFormat;
		LOG.trace("Output format set to " + outputFormat.getType());
	}
	

}
