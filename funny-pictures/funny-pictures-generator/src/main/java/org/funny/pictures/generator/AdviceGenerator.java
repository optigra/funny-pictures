package org.funny.pictures.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class AdviceGenerator {
	
	ConvertCmd cmd = new ConvertCmd();
	
	public Path generate(AdviceContext context) throws IOException, InterruptedException, IM4JavaException  {
		Path topCaption = null;
		Path bottomCaption = null;
		Path template = null;
		try{
			topCaption = generateCaption(context.getTopCaption(), 400, 50);
			bottomCaption = generateCaption(context.getBottomCaption(), 400, 50);
			template = prepareTemplateCopy(context.getTemplatePath());
			
			addCaption(template, topCaption, 0, 0);
			addCaption(template, bottomCaption, 0, 350);
			
			return template;
		}finally{
			if(topCaption != null) Files.deleteIfExists(topCaption);
			if(bottomCaption != null) Files.deleteIfExists(bottomCaption);
		}		
	}
	
	private Path generateCaption(String text, int width, int height) throws IOException, InterruptedException, IM4JavaException{
		Path result = Files.createTempFile("caption", ".bmp");
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
		
		cmd.run(op);
		return result;
	}
	
	private Path prepareTemplateCopy(Path templatePath) throws IOException, InterruptedException, IM4JavaException{
		Path result = Files.createTempFile("template", ".bmp");
		
		IMOperation op = new IMOperation();
		op.addImage(templatePath.toString());
		op.gravity("Center");
		op.crop(400, 400, 0, 0);
		op.p_repage();

		op.addImage(result.toString());
		
		
		ConvertCmd cmd = new ConvertCmd();
		cmd.run(op);
		
		return result;
	}
	
	private void addCaption(Path template, Path caption, int offsetX, int offsetY) throws IOException, InterruptedException, IM4JavaException{
		IMOperation op = new IMOperation();
		op.geometry(400, 400, offsetX, offsetY);
		op.addImage();
		op.addImage();
		op.addImage();
		CompositeCmd cmd = new CompositeCmd();
		cmd.run(op, caption.toString(), template.toString(), template.toString());
	}

}
