import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by gplzh on 16-3-18.
 */
@WebServlet("/createConsumer")
public class createConsumer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String SERVICE_PATH = "/configcenter/" + "service-" + request.getParameter("serviceName");
        ZkClient zkClient = new ZkClient("192.168.1.132:2181");

        if (zkClient.exists(SERVICE_PATH)) {
            List<String> serverList = zkClient.getChildren(SERVICE_PATH);
            for (String s : serverList) {
                response.getWriter().write(s);
            }
        } else {
            throw new RuntimeException("service not exist");
        }

        zkClient.subscribeChildChanges(SERVICE_PATH, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                List<String> serverList = list;
                for (String ss : serverList) {
                    getServletContext().log(ss);
                }
            }
        });

        response.getWriter().write("OK");
        response.getWriter().close();
    }
}
