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
@Entity(name="secUser")
@Table(name = "security_users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserImpl implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(nullable = false)
    protected String password;

    @Column(name = "is_enabled", columnDefinition = "BOOLEAN")
    protected Boolean isEnabled;

    @ManyToMany(targetEntity = GroupImpl.class, fetch = FetchType.LAZY)
    @JoinTable(name = "security_users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    protected Set<Group> groups;

    @ManyToMany(targetEntity = RoleImpl.class, fetch = FetchType.LAZY)
    @JoinTable(name = "security_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Set<Role> roles;
    
    @Column(name = "phone_no")
    protected String phoneNo;

    protected transient String plainPassword;

    protected transient String plainPasswordRepeated;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public Set<Group> getGroups() {
        return groups;
    }

    @Override
    public void addGroup(Group group) {
        this.groups.add(group);
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String getEmail() {
        return this.username;
    }

    @Override
    public void setEmail(String email) {
        this.username = email;
    }

    @Override
    public void removeGroup(Group group) {
        this.groups.remove(group);
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
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        if(this.getFirstName() != null && this.getLastName() != null) {
            return this.getFullName();
        } else {
            return this.getUsername();
        }
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPlainPasswordRepeated() {
        return plainPasswordRepeated;
    }

    public void setPlainPasswordRepeated(String plainPasswordRepeated) {
        this.plainPasswordRepeated = plainPasswordRepeated;
    }

    @Override
    public String getPhoneNo() {
        return phoneNo;
    }

    @Override
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    

}
