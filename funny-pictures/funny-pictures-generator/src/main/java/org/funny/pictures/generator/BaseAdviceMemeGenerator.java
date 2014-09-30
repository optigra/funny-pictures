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

	public final void setImageInfoExtractor(
			ImageInformationExtractor imageInfoExtractor) {
		this.imageInfoExtractor = imageInfoExtractor;
	}

	public BaseAdviceMemeGenerator() {
	}

	@Override
	public ImageHandle generate(AdviceMemeContext context) throws GeneratorException {

		LOG.debug("Generating an advice meme from context: " + context.toString());

		Path templateInput = null;
		Path topCaption = null;
		Path bottomCaption = null;
		Path result = null;
		try {			
			
			templateInput = toTempFile(context.getTemplateInputStream(), context.getMimeType().getExtension());
			result = prepareTemplate(templateInput);
			
			Dimension originalDimension = imageInfoExtractor.getImageDimension(result);
			int captionHeight =  (int) (captionHeightRatio*originalDimension.height);
			
			topCaption = generateCaption(context.getTopCaption(), originalDimension.width, captionHeight);
			bottomCaption = generateCaption(context.getBottomCaption(), originalDimension.width, captionHeight);

			addCaption(result, topCaption, originalDimension, 0, 0);
			addCaption(result, bottomCaption, originalDimension, 0, originalDimension.height-captionHeight);

			InputStream resultStream = new FileInputStream(result.toString());
			LOG.debug("Advice meme generated");

			return new ImageHandle(resultStream, outputFormat);

		} catch (Exception e) {
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

	private Path generateCaption(String text, int width, int height) throws IOException, InterruptedException, IM4JavaException {
		Path result = Files.createTempFile("caption", internalFormat.getExtension());
		IMOperation op = new IMOperation();
		op.size(width, height);
		op.background("transparent");
		op.fill("white");
		op.stroke("black");
		op.strokewidth(2);
		op.font("Impact-Regular");
		op.gravity("Center");
		op.addImage("label:" + text);
		op.addImage();

		Object[] args = new String[] { result.toString() };
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);

		convertCommand.run(op, args);

		return result;
	}

	private Path prepareTemplate(Path templatePath) throws IOException, InterruptedException, IM4JavaException {
		Path result = Files.createTempFile("template", internalFormat.getExtension());

		IMOperation op = new IMOperation();
		op.addImage();
		op.addImage();

		Object[] args = new String[] { templatePath.toString(), result.toString() };
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);
		convertCommand.run(op, args);

		return result;
	}

	private void addCaption(Path template, Path caption, Dimension resultDimension, int offsetX, int offsetY) throws IOException, InterruptedException, IM4JavaException {

		IMOperation op = new IMOperation();
		op.geometry(resultDimension.width, resultDimension.height, offsetX, offsetY);
		op.addImage();
		op.addImage();
		op.addImage();

		Object[] args = new String[] { caption.toString(), template.toString(), template.toString() };
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);
		compositeCommand.run(op, args);
	}

	private Path toTempFile(InputStream istream, String extensionSuffix) throws IOException {
		Path result = Files.createTempFile("template", extensionSuffix);
		Files.copy(istream, result, StandardCopyOption.REPLACE_EXISTING);
		LOG.debug("Created a temporary file: " + result.toAbsolutePath().toString());
		return result;
	}

	public MimeType getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(MimeType outputFormat) {
		this.outputFormat = outputFormat;
		LOG.trace("Output format set to " + outputFormat.getType());
	}
	

}
