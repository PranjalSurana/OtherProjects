package com.fidelity.unsafe;

public class Permission {
	
	String perm;

	public Permission(String perm) {
		super();
		this.perm = perm;
	}
	
	public Permission() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((perm == null) ? 0 : perm.hashCode());
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
		Permission other = (Permission) obj;
		if (perm == null) {
			if (other.perm != null)
				return false;
		} else if (!perm.equals(other.perm))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permission [perm=" + perm + "]";
	}

}
