package se.liu.ida.jprogress;

import se.liu.ida.jprogress.progressor.ProgressionStatus;
import se.liu.ida.jprogress.progressor.ProgressorProperties;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dnleng on 07/05/18.
 */
public class Logger {

    private Path destination;
    private int iteration;

    public Logger(String destination) {
        this.destination = Paths.get(destination);
        this.iteration = 1;

        try {
            Files.deleteIfExists(this.destination);
            Files.createFile(this.destination);
            List<String> lines = new LinkedList<>();
            lines.add("iteration,quality,true,false,unknown,none,bucket_0,bucket_0-1,bucket_1-5,bucket_5-10,bucket_10-20,bucket_20-30,bucket_30-50,bucket_50-70,bucket_70-80,bucket_80-90,bucket_90-95,bucket_95-99,bucket_99-100,d_prepare,d_expand,d_remove,d_sort,d_leak,d_total,n_components,n_vertices,n_edges");
            Files.write(this.destination, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(ProgressionStatus status, ProgressorProperties properties) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.iteration);
        sb.append(",");
        sb.append(status.getQuality());
        sb.append(",");
        sb.append(status.getTrueVerdict());
        sb.append(",");
        sb.append(status.getFalseVerdict());
        sb.append(",");
        sb.append(status.getUnknownVerdict());
        sb.append(",");
        sb.append(status.getNoVerdict());
        sb.append(",");
        sb.append(status.getZeroBucketSize());
        sb.append(",");
        sb.append(status.getBucketSize(0.0, 0.01));
        sb.append(",");
        sb.append(status.getBucketSize(0.01, 0.05));
        sb.append(",");
        sb.append(status.getBucketSize(0.05, 0.1));
        sb.append(",");
        sb.append(status.getBucketSize(0.1, 0.2));
        sb.append(",");
        sb.append(status.getBucketSize(0.2, 0.3));
        sb.append(",");
        sb.append(status.getBucketSize(0.3, 0.5));
        sb.append(",");
        sb.append(status.getBucketSize(0.5, 0.7));
        sb.append(",");
        sb.append(status.getBucketSize(0.7, 0.8));
        sb.append(",");
        sb.append(status.getBucketSize(0.8, 0.9));
        sb.append(",");
        sb.append(status.getBucketSize(0.9, 0.95));
        sb.append(",");
        sb.append(status.getBucketSize(0.95, 0.99));
        sb.append(",");
        sb.append(status.getBucketSize(0.99, 1.0));
        sb.append(",");
        for(int i = 1 ; i < status.getPerformance().length; i++) {
            sb.append(status.getPerformance()[i]-status.getPerformance()[i-1]);
            sb.append(",");
        }
        sb.append(status.getPerformance()[status.getPerformance().length-1]-status.getPerformance()[0]);
        sb.append(",");
        sb.append(properties.getComponentCount());
        sb.append(",");
        sb.append(properties.getVertexCount());
        sb.append(",");
        sb.append(properties.getEdgeCount());

        List<String> lines = new LinkedList<>();
        lines.add(sb.toString());

        try {
            Files.write(this.destination, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.iteration++;
    }
}
