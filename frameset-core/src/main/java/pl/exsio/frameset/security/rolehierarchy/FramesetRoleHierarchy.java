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
package pl.exsio.frameset.security.rolehierarchy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.exsio.frameset.security.model.Role;
import pl.exsio.frameset.security.repository.provider.SecurityRepositoryProvider;

/**
 *
 * @author exsio
 */

public class FramesetRoleHierarchy implements RoleHierarchy {

    private SecurityRepositoryProvider securityRepositories;

    @Override
    @Cacheable(value="roleHierarchy")
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> clctn) {

        Set<GrantedAuthority> reachable = new HashSet<>();
        for (GrantedAuthority ga : clctn) {
            reachable.add(ga);
            Role role = this.securityRepositories.getRoleRepository().findOneByName(ga.getAuthority());
            if (role != null) {
                reachable.addAll(this.getGrantedAuthoritiesRecurse(role));
            }
        }
        return reachable;
    }

    private Set<GrantedAuthority> getGrantedAuthoritiesRecurse(Role role) {
        Set<GrantedAuthority> recurse = new HashSet<>();
        if (role.getChildRoles().size() > 0) {
            for (Role childRole : role.getChildRoles()) {
                if (!childRole.equals(role)) {
                    recurse.addAll(this.getGrantedAuthoritiesRecurse(childRole));
                }
            }
        }
        recurse.add(new SimpleGrantedAuthority(role.getName()));
        return recurse;
    }

    public void setSecurityRepositories(SecurityRepositoryProvider securityRepositories) {
        this.securityRepositories = securityRepositories;
    }

}
