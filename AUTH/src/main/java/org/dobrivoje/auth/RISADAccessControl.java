package org.dobrivoje.auth;

public class RISADAccessControl extends ShiroAccessControl {

    public RISADAccessControl() {
        super("classpath:RisAD.ini");
    }
}
