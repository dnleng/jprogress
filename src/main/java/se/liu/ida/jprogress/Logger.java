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
        if(!Files.exists(this.destination)) {
            try {
                Files.createFile(this.destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.iteration = 0;
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
        for(int i = 0 ; i < status.getPerformance().length; i++) {
            sb.append(status.getPerformance()[i]);
            sb.append(",");
        }
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
