package pro.analyticslab.bot.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.net.URL;

public class SshTunnel {
    private final Logger logger = LoggerFactory.getLogger(SshTunnel.class);
    private Session session;


    /**
     * Initializes SSH-tunnel connection
     * @param host remote ip/host
     * @param user remove server user
     * @param certificatePath ssh-rsa auth certificate path
     * @param sshPort SSH server port
     */
    public void connect(
        @Nonnull String host,
        @Nonnull String user,
        @Nonnull String certificatePath,
        int sshPort
    ) throws Exception {
        URL url = SshTunnel.class.getClassLoader().getResource(certificatePath);
        if (url == null)
            throw new IllegalArgumentException("Invalid SSH key path.");

        JSch jSch = new JSch();
        jSch.addIdentity(url.getPath());

        session = jSch.getSession(user, host, sshPort);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();
        logger.info("SSH tunnel created on 0.0.0.0");
    }


    /**
     * Forwards remote port to local
     * @param remotePort remote server port
     * @param localPort local available port
     */
    public void forward(
            int remotePort,
            int localPort
    ) {

    }
}
