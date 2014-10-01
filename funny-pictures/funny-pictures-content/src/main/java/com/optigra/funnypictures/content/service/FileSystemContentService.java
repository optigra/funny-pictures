package com.optigra.funnypictures.content.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.optigra.funnypictures.content.ContentReadException;
import com.optigra.funnypictures.content.ContentSaveException;
import com.optigra.funnypictures.content.model.Content;
import com.optigra.funnypictures.model.content.MimeType;

/**
 * Service, that allow you to make different manipulations with content.
 * 
 * @author oyats
 * 
 */

public class FileSystemContentService implements ContentService {

	private static final Logger LOG = LoggerFactory.getLogger(FileSystemContentService.class);

	private static final char EXTENSION_SEPARATOR = '.';

	private String contentRepositoryPath;

	@Override
	public Content getContentByPath(final String path) {
		Content content = null;

		LOG.info("Read file from " + path);

		try {
			File file = new File(getFullfilePath(path));
			FileInputStream fileInputStream = new FileInputStream(file);
			String fileExtention = FilenameUtils.getExtension(file.getName());

			content = new Content();
			content.setContentStream(fileInputStream);
			content.setPath(path);
			content.setSize(Long.valueOf(file.getTotalSpace()));
			content.setMimeType(MimeType.fromExtension(EXTENSION_SEPARATOR + fileExtention));

			fileInputStream.close();
		} catch (FileNotFoundException e) {
			LOG.error("Can't read file from file system", e);
			throw new ContentReadException(e);
		} catch (IOException e) {
			LOG.error("Can't close file input stream", e);
			throw new ContentReadException(e);
		}

		return content;
	}

	@Override
	public void saveContent(final Content content) {
		LOG.info("Save content: {}", content);

		File file = new File(getFullfilePath(content.getPath()));

		try {

			if (file.createNewFile()) {
				IOUtils.copy(content.getContentStream(), new FileOutputStream(file));
			}

		} catch (Exception e) {
			LOG.error("Can't save file", e);
			throw new ContentSaveException("Can't save file", e);
		}
	}

	/**
	 * Method, that allows you to get Full File Path.
	 * 
	 * @param filePath
	 *            Input file path, for which you want to find full file path.
	 * @return Returns full path of file.
	 */

	private String getFullfilePath(final String filePath) {
		return getContentRepositoryPath() + filePath;
	}

	public String getContentRepositoryPath() {
		return contentRepositoryPath;
	}

	public void setContentRepositoryPath(final String contentRepositoryPath) {
		this.contentRepositoryPath = contentRepositoryPath;
	}
}
