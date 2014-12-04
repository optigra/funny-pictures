package com.optigra.funnypictures.content.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
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
			AutoCloseInputStream  fileInputStream = new AutoCloseInputStream(new FileInputStream(file));
			String fileExtension = FilenameUtils.getExtension(file.getName());

			content = new Content();
			content.setContentStream(fileInputStream);
			content.setPath(path);
			content.setSize(Long.valueOf(file.getTotalSpace()));
			content.setMimeType(MimeType.fromExtension(EXTENSION_SEPARATOR + fileExtension));

		} catch (FileNotFoundException e) {
			String message = "Content not found";
			LOG.error("Can't read file from file system", e);
			throw new ContentReadException(message);
		}
		
		return content;
	}

	@Override
	public void saveContent(final Content content) {
		LOG.info("Save content: {}", content);

		try {
			File file = new File(getFullfilePath(content.getPath()));
			if (file.createNewFile()) {
				IOUtils.copy(content.getContentStream(), new FileOutputStream(file));
			}

		} catch (Exception e) {
			LOG.error("Can't save file", e);
			throw new ContentSaveException("Could not save content to file", e);
		} finally {
			try {
				if (content.getContentStream() != null) {
					content.getContentStream().close();
				}
				/*
				 * TODO
				 * The content input stream has been closed,
				 * but the file it was read from (if any) still exists.
				 * Find a way to automatically remove that file.
				 */
			} catch (IOException e) {
				throw new ContentSaveException("Could not close the content input stream", e);
			}
			
		}
	}

	/**
	 * Method, that allows you to get Full File Path.
	 * 
	 * @param filePath
	 *            Input file path, for which you want to find full file path.
	 * @return Full path of file.
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
