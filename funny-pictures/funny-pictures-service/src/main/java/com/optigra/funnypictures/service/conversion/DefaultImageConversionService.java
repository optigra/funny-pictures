package com.optigra.funnypictures.service.conversion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.annotation.Resource;

import org.apache.commons.io.input.AutoCloseInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.optigra.funnypictures.generator.api.ImageHandle;
import com.optigra.funnypictures.generator.util.ImageConverter;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * Default implementation of ImageConversionService.
 * @author odisseus
 *
 */
@Service("imageConversionService")
public class DefaultImageConversionService implements ImageConversionService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultImageConversionService.class);
	
	private static final String TMP_FILE_NAME_BASE = "tmp_conversion";
	
	@Resource(name = "imageConverter")
	private ImageConverter imageConverter;

	@Override
	public ImageHandle convert(final ImageHandle source, final MimeType targetFormat) {
		try {
			
			Path sourceImagePath = toTempFile(source.getImageInputStream(), source.getImageFormat().getExtension());
			Path targetImagePath = Files.createTempFile(TMP_FILE_NAME_BASE, targetFormat.getExtension());
			imageConverter.convert(sourceImagePath, targetImagePath, targetFormat);
			
			InputStream resultStream = new AutoCloseInputStream(new FileInputStream(targetImagePath.toString()));

			return new ImageHandle(resultStream, targetFormat);
			
		} catch (IOException e) {
			throw new ImageConversionException(e);
		}
	}
	
	/**
	 * Writes data from an input stream to temporary file.
	 * @param istream input stream
	 * @param extensionSuffix extension of file to generate
	 * @return path to generated file
	 * @throws IOException when an IO problem occurs
	 */
	private Path toTempFile(final InputStream istream, final String extensionSuffix) throws IOException {
		Path result = Files.createTempFile(TMP_FILE_NAME_BASE, extensionSuffix);
		long bytesCopied = Files.copy(istream, result, StandardCopyOption.REPLACE_EXISTING);
		
		if (bytesCopied == 0) {
			LOG.warn("The input stream was empty");
		}
		LOG.debug("Created a temporary file: {} Size: {} bytes ", result.toAbsolutePath().toString(), bytesCopied);
		
		return result;
	}

}
