package com.optigra.funnypictures.facade.resources.content;

import javax.annotation.Resource;

import com.optigra.funnypictures.service.repository.RepositoryMonitorService;

/**
 * A naming strategy that monitors the repository and gives the next unused name to the new item.
 * @author odisseus
 *
 */
public class SequentialNamingStrategy implements ContentResourceNamingStrategy {
	
	@Resource(name = "repositoryMonitorService")
	private RepositoryMonitorService repositoryMonitorService;

	@Override
	public String createIdentifier(final ContentResource resource) {
		String nextName = repositoryMonitorService.getNextFreeIdentifier();
		return "/" + nextName + resource.getMimeType().getExtension();
	}

	@Override
	public String createIdentifier(final String prefix, final ContentResource resource) {
		String nextName = repositoryMonitorService.getNextFreeIdentifier();
		return "/" + prefix + "/" + nextName + resource.getMimeType().getExtension();
	}

	public final RepositoryMonitorService getRepositoryMonitorService() {
		return repositoryMonitorService;
	}

	public final void setRepositoryMonitorService(final RepositoryMonitorService repositoryMonitorService) {
		this.repositoryMonitorService = repositoryMonitorService;
	}
	

}
