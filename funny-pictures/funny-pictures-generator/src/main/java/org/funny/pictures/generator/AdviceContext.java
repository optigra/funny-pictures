package org.funny.pictures.generator;

import java.nio.file.Path;

public class AdviceContext {
	
	private final Path templatePath;
	
	private final String topCaption;
	
	private final String bottomCaption;

	public AdviceContext(Path templatePath, String topCaption,
			String bottomCaption) {
		super();
		this.templatePath = templatePath;
		this.topCaption = topCaption;
		this.bottomCaption = bottomCaption;
	}

	public Path getTemplatePath() {
		return templatePath;
	}

	public String getTopCaption() {
		return topCaption;
	}

	public String getBottomCaption() {
		return bottomCaption;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bottomCaption == null) ? 0 : bottomCaption.hashCode());
		result = prime * result
				+ ((templatePath == null) ? 0 : templatePath.hashCode());
		result = prime * result
				+ ((topCaption == null) ? 0 : topCaption.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdviceContext other = (AdviceContext) obj;
		if (bottomCaption == null) {
			if (other.bottomCaption != null)
				return false;
		} else if (!bottomCaption.equals(other.bottomCaption))
			return false;
		if (templatePath == null) {
			if (other.templatePath != null)
				return false;
		} else if (!templatePath.equals(other.templatePath))
			return false;
		if (topCaption == null) {
			if (other.topCaption != null)
				return false;
		} else if (!topCaption.equals(other.topCaption))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AdviceContext [templatePath=" + templatePath + ", topCaption="
				+ topCaption + ", bottomCaption=" + bottomCaption + "]";
	}
	
	

}
