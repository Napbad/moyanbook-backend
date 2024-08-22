package com.moyanshushe.controller;

/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/5/24 4:29 PM
    @Description: 

*/

import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.lang.management.OperatingSystemMXBean;

@Api

@RestController
@RequestMapping("/test")
public class TestController {

    private static final int BYTES_IN_KB = 1024;
    private static final int BYTES_IN_MB = BYTES_IN_KB * 1024;
    private static final int BYTES_IN_GB = BYTES_IN_MB * 1024;

    /**
     * enum OrderRule {
     * DESC, ASC
     * }
     * <p>
     * Order.Status {
     * short NORMAL = 10;
     * short WAIT_FOR_PAYING = 20;
     * short FINISHED = 30;
     * }
     * <p>
     * User/Member.Status {
     * short NORMAL = 1;
     * short UNSAFE = 2;
     * short FREEZE = 3;
     * }
     * User.Type {
     * short NORMAL_USER = 1;
     * short STORE_USER = 2;
     * }
     * Item.Status {
     * <p>
     * short IN_USER = 00;
     * short NORMAL = 10;
     * short WAIT_FOR_PAYING = 20;
     * short WAIT_FOR_SENDING = 30;
     * short WAIT_FOR_RECEIVING = 40;
     * short FINISHED = 50;
     * }
     * Comment.Status {
     * int HIDDEN = 0;
     * int VISIBLE = 1;
     * int DELETED = 2;
     * }
     * Coupon.Status {
     * short NORMAL = 1;
     * short EXPIRED = 2;
     * }
     * <p>
     * }
     */
    @Api
    @GetMapping("/infos")
    public String infos() {
        return "     * enum OrderRule {\n" +
                "     * DESC, ASC\n" +
                "     * }\n" +
                "     * <p>\n" +
                "     * Order.Status {\n" +
                "     * short NORMAL = 10;\n" +
                "     * short WAIT_FOR_PAYING = 20;\n" +
                "     * short FINISHED = 30;\n" +
                "     * }\n" +
                "     * <p>\n" +
                "     * User/Member.Status {\n" +
                "     * short NORMAL = 1;\n" +
                "     * short UNSAFE = 2;\n" +
                "     * short FREEZE = 3;\n" +
                "     * }\n" +
                "     * User.Type {\n" +
                "     * short NORMAL_USER = 1;\n" +
                "     * short STORE_USER = 2;\n" +
                "     * }\n" +
                "     * Item.Status {\n" +
                "     * <p>\n" +
                "     * short IN_USER = 00;\n" +
                "     * short NORMAL = 10;\n" +
                "     * short WAIT_FOR_PAYING = 20;\n" +
                "     * short WAIT_FOR_SENDING = 30;\n" +
                "     * short WAIT_FOR_RECEIVING = 40;\n" +
                "     * short FINISHED = 50;\n" +
                "     * }\n" +
                "     * Comment.Status {\n" +
                "     * int HIDDEN = 0;\n" +
                "     * int VISIBLE = 1;\n" +
                "     * int DELETED = 2;\n" +
                "     * }\n" +
                "     * Coupon.Status {\n" +
                "     * short NORMAL = 1;\n" +
                "     * short EXPIRED = 2;\n" +
                "     * }";
    }


    @GetMapping(value = "/info", produces = MediaType.TEXT_HTML_VALUE)
    public String info() {
        StringBuilder infoBuilder = new StringBuilder();

        // Get memory usage
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        infoBuilder.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<title>System Information</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; }")
                .append("table { border-collapse: collapse; width: 50%; margin: auto; }")
                .append("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }")
                .append("th { background-color: #f2f2f2; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<h1>System Information</h1>");

        infoBuilder.append("<h2>Memory Usage</h2>")
                .append("<table>")
                .append("<tr><th>Heap Memory Usage</th></tr>")
                .append(formatMemoryRow("Init", heapMemoryUsage.getInit()))
                .append(formatMemoryRow("Used", heapMemoryUsage.getUsed()))
                .append(formatMemoryRow("Committed", heapMemoryUsage.getCommitted()))
                .append(formatMemoryRow("Max", heapMemoryUsage.getMax()));

        infoBuilder.append("<tr><th>Non-Heap Memory Usage</th></tr>")
                .append(formatMemoryRow("Init", nonHeapMemoryUsage.getInit()))
                .append(formatMemoryRow("Used", nonHeapMemoryUsage.getUsed()))
                .append(formatMemoryRow("Committed", nonHeapMemoryUsage.getCommitted()))
//                .append(formatMemoryRow("Max", nonHeapMemoryUsage.getMax()))
                .append("</table>");

        // Get CPU usage
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        double processCpuLoad = osMXBean.getAvailableProcessors();
        double systemCpuLoad = osMXBean.getSystemLoadAverage();

        infoBuilder.append("<h2>CPU Usage</h2>")
                .append("<table>")
//                .append("<tr><th>Process CPU Load</th><td>").append(formatCpuLoad(processCpuLoad)).append("</td></tr>")
                .append("<tr><th>System CPU Load</th><td>").append(formatCpuLoad(systemCpuLoad)).append("</td></tr>")
                .append("</table>");

        // Get active threads
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long threadCount = threadMXBean.getThreadCount();
        long peakThreadCount = threadMXBean.getPeakThreadCount();
        long totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();

        infoBuilder.append("<h2>Thread Information</h2>")
                .append("<table>")
                .append("<tr><th>Active Threads</th><td>").append(threadCount).append("</td></tr>")
                .append("<tr><th>Peak Thread Count</th><td>").append(peakThreadCount).append("</td></tr>")
                .append("<tr><th>Total Started Threads</th><td>").append(totalStartedThreadCount).append("</td></tr>")
                .append("</table>");

        infoBuilder.append("</body>")
                .append("</html>");

        // Return the HTML content
        return infoBuilder.toString();
    }

    private String formatMemoryRow(String title, long value) {
        String formattedValue = formatMemory(value);
        return "<tr><td>" + title + ": " + formattedValue + "</td></tr>";
    }

    private String formatMemory(long bytes) {
        if (bytes >= BYTES_IN_GB) {
            return String.format("%.2f GB", (double) bytes / BYTES_IN_GB);
        } else if (bytes >= BYTES_IN_MB) {
            return String.format("%.2f MB", (double) bytes / BYTES_IN_MB);
        } else if (bytes >= BYTES_IN_KB) {
            return String.format("%.2f KB", (double) bytes / BYTES_IN_KB);
        } else {
            return bytes + " B";
        }
    }

    private String formatCpuLoad(double load) {
        if (load == -1.0) {
            return "Not Available";
        }
        return String.format("%.2f%%", load * 100);
    }
}