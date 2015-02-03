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
package pl.exsio.frameset.security.acl;

import java.util.LinkedList;
import java.util.List;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.exsio.frameset.security.userdetails.UserDetailsProvider;

/**
 *
 * @author exsio
 */
@Transactional(propagation = Propagation.REQUIRED)
public class AclManagerImpl implements AclManager {

    private MutableAclService aclService;

    private RoleHierarchy roleHierarchy;

    @Override
    public boolean isGranted(AclSubject subject, final Permission permission) {
        MutableAcl acl = this.getAcl(subject);
        try {
            return acl.isGranted(new LinkedList<Permission>() {
                {
                    add(permission);
                }
            }, this.getCurrentSids(), true);
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    public boolean isGranted(AclSubject subject, List<Permission> permissions) {
        MutableAcl acl = this.getAcl(subject);
        try {
            return acl.isGranted(permissions, this.getCurrentSids(), true);
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    public boolean isGranted(AclSubject subject, List<Permission> permissions, List<Sid> sids) {
        MutableAcl acl = this.getAcl(subject);
        try {
            return acl.isGranted(permissions, sids, true);
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    public boolean isGranted(AclSubject subject, final Permission permission, final Sid sid) {
        MutableAcl acl = this.getAcl(subject);
        try {
            return acl.isGranted(new LinkedList<Permission>() {
                {
                    add(permission);
                }
            }, new LinkedList<Sid>() {
                {
                    add(sid);
                }
            }, true);
        } catch (NotFoundException ex) {
            return false;
        }
    }

    private List<Sid> getCurrentSids() {
        List<Sid> sids = new LinkedList<>();
        try {
            for (GrantedAuthority ga : this.roleHierarchy.getReachableGrantedAuthorities(UserDetailsProvider.getUserDetails().getAuthorities())) {
                sids.add(new GrantedAuthoritySid(ga));
            }
        } catch (NullPointerException e) {
            // this shoudn't happen, but it may occur when url intercepts are misconfigured
            return sids;
        }
        return sids;
    }

    private MutableAcl getAcl(AclSubject subject) {
        ObjectIdentity oid = AclOidFactory.create(subject);
        MutableAcl acl = null;
        try {
            acl = (MutableAcl) aclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = aclService.createAcl(oid);
        }
        return acl;
    }

    @Override
    public void grant(AclSubject subject, Permission permission, Sid sid) {
        MutableAcl acl = this.getAcl(subject);
        if (!this.isGranted(subject, permission, sid)) {
            acl.insertAce(acl.getEntries().size(), permission, sid, true);
            aclService.updateAcl(acl);
        }
    }

    @Override
    public void grant(AclSubject subject, List<Permission> permissions, Sid sid) {
        MutableAcl acl = this.getAcl(subject);
        boolean update = false;
        for (Permission permission : permissions) {
            if (!this.isGranted(subject, permission, sid)) {
                acl.insertAce(acl.getEntries().size(), permission, sid, true);
                update = true;
            }
        }
        if (update) {
            aclService.updateAcl(acl);
        }
    }

    @Override
    public void revoke(AclSubject subject, Permission permission, Sid sid) {
        MutableAcl acl = this.getAcl(subject);
        List<AccessControlEntry> entries = acl.getEntries();
        boolean update = false;
        for (int i = 0; i < entries.size(); i++) {
            if (this.sidHasGivenPermission(entries.get(i), sid, permission)) {
                acl.deleteAce(i);
                update = true;
            }
        }
        if (update) {
            aclService.updateAcl(acl);
        }
    }

    @Override
    public void revoke(AclSubject subject, List<Permission> permissions, Sid sid) {
        MutableAcl acl = this.getAcl(subject);
        List<AccessControlEntry> entries = acl.getEntries();
        boolean update = false;
        for (int i = 0; i < entries.size(); i++) {
            for (Permission permission : permissions) {
                if (this.sidHasGivenPermission(entries.get(i), sid, permission)) {
                    acl.deleteAce(i);
                    update = true;
                }
            }
        }
        if (update) {
            aclService.updateAcl(acl);
        }
    }

    private boolean sidHasGivenPermission(AccessControlEntry entry, Sid sid, Permission permission) {
        return entry.getSid().equals(sid) && entry.getPermission().equals(permission);
    }

    public void setAclService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

}
