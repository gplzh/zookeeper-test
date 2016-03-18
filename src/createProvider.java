import org.I0Itec.zkclient.ZkClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by gplzh on 16-3-18.
 */
@WebServlet("/createProvider")
public class createProvider extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String serviceName = "service-" + request.getParameter("serviceName");
        String PATH = "/configcenter";
        ZkClient zkClient = new ZkClient("192.168.1.132:2181");

        if (!zkClient.exists(PATH)) {
            zkClient.createPersistent(PATH);
        }
        if (!zkClient.exists(PATH + "/" + serviceName)) {
            zkClient.createPersistent(PATH + "/" + serviceName);
        }

        String ip = InetAddress.getLocalHost().getHostAddress();

        zkClient.createEphemeral(PATH + "/" + serviceName + "/" + ip);

        response.getWriter().write("OK");
        response.getWriter().close();

    }
}