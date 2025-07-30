package com.sdp.poc.threading.base.system;

public class Exit {

    public void captureExit() {
        SecurityManager originalSecurityManager = System.getSecurityManager();

        System.setSecurityManager(new SecurityManager() {
            public void checkExit(int status) {
                    throw new SecurityException("System.exit(" + status + ") interceptado");
            }
           @Override
           public void checkPermission(java.security.Permission perm) {
                // Permitir todo lo demás
           }
        });
    }

}
