/* 
 * The MIT License
 *
 * Copyright 2014 exsio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.exsio.frameset.security.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import pl.exsio.frameset.security.model.Group;
import pl.exsio.frameset.security.model.Role;
import pl.exsio.frameset.security.model.User;

/**
 *
 * @author exsio
 */
@Entity(name="secGroup")
@Table(name = "security_groups")
@Inheritance(strategy = InheritanceType.JOINED)
public class GroupImpl implements Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(name = "group_name")
    protected String name;

    @ManyToMany(targetEntity = RoleImpl.class, fetch = FetchType.LAZY)
    @JoinTable(name = "security_groups_roles",
        joinColumns = @JoinColumn(name = "group_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles;

    @ManyToMany(targetEntity = UserImpl.class, mappedBy = "groups", fetch = FetchType.LAZY)
    protected Set<User> users;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public Set<User> getUsers() {
        return users;
    }

    @Override
    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    @Override
    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public void removeUser(User user) {
        this.users.remove(user);
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

}
