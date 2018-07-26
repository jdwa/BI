package com.ldchotels.system.dao;

import java.util.List;
import com.ldchotels.system.model.Role;

public interface RoleDao {
	public Role findById(Long id);
	public Role findByCode(String code);
	public Role add(Role role);
	public Role delete(Long id);
	public Role detail(Long id);
	public Role update(Role role);
	public List<Role> list();
}
