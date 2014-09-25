package org.funny.pictures.generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.funny.pictures.generator.api.AdviceMemeContext;
import org.funny.pictures.generator.api.AdviceMemeGenerator;
import org.funny.pictures.generator.api.GeneratorException;
import org.funny.pictures.generator.api.ImageHandle;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.model.content.MimeType;

public class BaseAdviceMemeGenerator implements AdviceMemeGenerator {
	
	public static final MimeType DEFAULT_OUTPUT_FORMAT = MimeType.IMAGE_PNG_PNG;
	
	private static final Logger LOG = LoggerFactory.getLogger(BaseAdviceMemeGenerator.class);
	
	private final MimeType internalFormat = MimeType.IMAGE_BMP_BMP;
	
	private MimeType outputFormat;
	
	private ConvertCmd convertCommand = new ConvertCmd();
	
	private CompositeCmd compositeCommand = new CompositeCmd();
	
	public BaseAdviceMemeGenerator() {
	}

	@Override
	public ImageHandle generate(AdviceMemeContext context) throws IOException, InterruptedException, GeneratorException{
		
		LOG.debug("Generating an advice meme from context: " + context.toString());
		
		Path templateInput = 
				toTempFile(context.getTemplateInputStream(), context.getMimeType().getExtension());
		Path topCaption = null;
		Path bottomCaption = null;
		Path result = null;
		try{
			result = prepareTemplate(templateInput);
			topCaption = generateCaption(context.getTopCaption(), 400, 50);
			bottomCaption = generateCaption(context.getBottomCaption(), 400, 50);
			
			addCaption(result, topCaption, 0, 0);
			addCaption(result, bottomCaption, 0, 350);
			
			InputStream resultStream = new FileInputStream(result.toString());
			LOG.debug("Advice meme generated");
			return new ImageHandle(resultStream, outputFormat);
			
		}catch(IM4JavaException e){
			LOG.error("Internal generation procedure failed");
			throw new GeneratorException(e.getMessage(), e);
		}finally{
			LOG.debug("Deleting temporary files");
			if(templateInput != null){
				Files.deleteIfExists(templateInput);
			}
			if(topCaption != null){
				Files.deleteIfExists(topCaption);
			}
			if(bottomCaption != null){
				Files.deleteIfExists(bottomCaption);
			}
			LOG.debug("Temporary files deleted");
		}
	}
	
	private Path generateCaption(String text, int width, int height) throws IOException, InterruptedException, IM4JavaException{
		Path result = Files.createTempFile("caption", internalFormat.getExtension());
		IMOperation op = new IMOperation();
		op.size(width, height);
		op.background("transparent");
		op.fill("white");
		op.stroke("black");
		op.strokewidth(2);
		op.font("Impact-Regular");
		op.gravity("Center");
		op.addImage("label:"+text);
		op.addImage();
		
		Object[] args = new String[]{result.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);		

		convertCommand.run(op, args);
		
		return result;
	}
	
	private Path prepareTemplate(Path templatePath) throws IOException, InterruptedException, IM4JavaException{
		Path result = Files.createTempFile("template", internalFormat.getExtension());
		
		IMOperation op = new IMOperation();
		op.addImage();
		op.gravity("Center");
		op.crop(400, 400, 0, 0);
		op.p_repage();
		op.addImage();		
		
		Object[] args = new String[]{templatePath.toString(), result.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);		
		convertCommand.run(op, args);
		
		return result;
	}
	
	private void addCaption(Path template, Path caption, int offsetX, int offsetY) throws IOException, InterruptedException, IM4JavaException{
		
		IMOperation op = new IMOperation();
		op.geometry(400, 400, offsetX, offsetY);
		op.addImage();
		op.addImage();
		op.addImage();
		
		Object[] args = new String[]{caption.toString(), template.toString(), template.toString()};
		LOG.debug("Running command [%1%s] with arguments %2$s ", op.toString(), args);
		compositeCommand.run(op, args);
	}
	
	private Path toTempFile(InputStream istream, String extensionSuffix) throws IOException{
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
