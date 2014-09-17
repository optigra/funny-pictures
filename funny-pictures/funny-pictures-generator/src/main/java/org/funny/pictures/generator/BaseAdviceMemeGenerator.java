package org.funny.pictures.generator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.funny.pictures.generator.api.AdviceMemeContext;
import org.funny.pictures.generator.api.AdviceMemeGenerator;
import org.funny.pictures.generator.api.ImageHandle;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.model.content.MimeType;

public class BaseAdviceMemeGenerator implements AdviceMemeGenerator {
	
	public static final MimeType DEFAULT_OUTPUT_FORMAT = MimeType.IMAGE_PNG_PNG;
	
	private final MimeType internalFormat = MimeType.IMAGE_BMP_BMP;
	
	private MimeType outputFormat;
	
	public BaseAdviceMemeGenerator() {
		this(DEFAULT_OUTPUT_FORMAT);
	}
	
	public BaseAdviceMemeGenerator(MimeType outputFormat) {
		this.outputFormat = outputFormat;
	}

	@Override
	public ImageHandle generate(AdviceMemeContext context) throws IOException, InterruptedException, IM4JavaException{
			
		Path templateInput = toTempFile(context.getTemplateInputStream(), 
				context.getMimeType().getExtension());
		Path topCaption = null;
		Path bottomCaption = null;
		Path result = null;
		try{
			topCaption = generateCaption(context.getTopCaption(), 400, 50);
			bottomCaption = generateCaption(context.getBottomCaption(), 400, 50);
			result = prepareTemplate(templateInput);
			
			addCaption(result, topCaption, 0, 0);
			addCaption(result, bottomCaption, 0, 350);
			
			InputStream resultStream = new FileInputStream(result.toString());
			return new ImageHandle(resultStream, outputFormat);
			
		}finally{
			if(templateInput != null) Files.deleteIfExists(templateInput);
			if(topCaption != null) Files.deleteIfExists(topCaption);
			if(bottomCaption != null) Files.deleteIfExists(bottomCaption);
		}
	}
	
	private Path generateCaption(String text, int width, int height) throws IOException, InterruptedException, IM4JavaException{
		Path result = Files.createTempFile("caption", internalFormat.getExtension());
		IMOperation op = new IMOperation();
		op.size(400, 50);
		op.background("transparent");
		op.fill("white");
		op.stroke("black");
		op.strokewidth(2);
		op.font("Impact-Regular");
		op.gravity("Center");
		op.addImage("label:"+text);
		op.addImage(result.toString());
		
		ConvertCmd convertCommand = new ConvertCmd();
		convertCommand.run(op);
		
		return result;
	}
	
	private Path prepareTemplate(Path templatePath) throws IOException, InterruptedException, IM4JavaException{
		Path result = Files.createTempFile("template", internalFormat.getExtension());
		
		IMOperation op = new IMOperation();
		op.addImage(templatePath.toString());
		op.gravity("Center");
		op.crop(400, 400, 0, 0);
		op.p_repage();
		op.addImage(result.toString());		
		
		ConvertCmd convertCommand = new ConvertCmd();
		convertCommand.run(op);
		
		return result;
	}
	
	private void addCaption(Path template, Path caption, int offsetX, int offsetY) throws IOException, InterruptedException, IM4JavaException{
		IMOperation op = new IMOperation();
		op.geometry(400, 400, offsetX, offsetY);
		op.addImage();
		op.addImage();
		op.addImage();
		CompositeCmd compositeCommand = new CompositeCmd();
		compositeCommand.run(op, caption.toString(), template.toString(), template.toString());
	}
	
	private Path toTempFile(InputStream istream, String extensionSuffix) throws IOException{
		Path result = Files.createTempFile("template", extensionSuffix);
		Files.copy(istream, result, StandardCopyOption.REPLACE_EXISTING);
		return result;
	}

	public MimeType getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(MimeType outputFormat) {
		this.outputFormat = outputFormat;
	}
	

}
