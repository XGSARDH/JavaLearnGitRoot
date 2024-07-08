import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应的类型为图片
        response.setContentType("image/jpg");

        // 从 servlet 初始化参数获取图片路径
        String imagePath = getServletContext().getRealPath("/") + "WEB-INF/classes/upload/ascll.jpg";

        File file = new File(imagePath);

        // 检查文件是否存在
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Requested image not found.");
            return;
        }

        response.setContentLength((int) file.length());

        // 使用 try-with-resources 自动关闭资源
        try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096]; // 使用更大的缓冲区
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            out.flush();
        } catch (IOException e) {
            // 对于发生的 I/O 异常，可以记录日志或者发送错误响应
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the image.");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
