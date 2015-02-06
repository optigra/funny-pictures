package com.optigra.funnypictures.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Common model for all views.
 *  
 * @author oleh.zasadnyy
 *
 */
@MappedSuperclass
public class View {
	 private static final long serialVersionUID = 1L;

	    @Id
	    @Column(name = "id")
	    private Long id;

	    @Column(name = "create_date")
	    private Date createDate;

	    @Column(name = "update_date")
	    private Date updateDate;

	    public Long getId() {
	        return id;
	    }

	    public void setId(final Long id) {
	        this.id = id;
	    }

	    public Date getCreateDate() {
	        return new Date(createDate.getTime());
	    }

	    public void setCreateDate(final Date createDate) {
	        this.createDate = new Date(createDate.getTime());
	    }

	    public Date getUpdateDate() {
	        return new Date(updateDate.getTime());
	    }

	    public void setUpdateDate(final Date updateDate) {
	        this.updateDate = new Date(updateDate.getTime());
	    }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((createDate == null) ? 0 : createDate.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result
					+ ((updateDate == null) ? 0 : updateDate.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			View other = (View) obj;
			if (createDate == null) {
				if (other.createDate != null) {
					return false;
				}
			} else if (!createDate.equals(other.createDate)) {
				return false;
			}
			if (id == null) {
				if (other.id != null) {
					return false;
				}
			} else if (!id.equals(other.id)) {
				return false;
			}
			if (updateDate == null) {
				if (other.updateDate != null) {
					return false;
				}
			} else if (!updateDate.equals(other.updateDate)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "View [id=" + id + ", createDate=" + createDate
					+ ", updateDate=" + updateDate + "]";
		}
}
