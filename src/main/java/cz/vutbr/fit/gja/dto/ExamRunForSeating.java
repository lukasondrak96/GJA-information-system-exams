package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRun;

import java.util.List;

public class ExamRunForSeating {
    private String date;
    private String startTime;
    private String endTime;
    private BlocksDto blocks;
    private List<List<BlockOnExamRun>> seating;

    public ExamRunForSeating() {
    }

    public ExamRunForSeating(String date, String startTime, String endTime, BlocksDto blocks, List<List<BlockOnExamRun>> seating) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.blocks = blocks;
        this.seating = seating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BlocksDto getBlocks() {
        return blocks;
    }

    public void setBlocks(BlocksDto blocks) {
        this.blocks = blocks;
    }

    public List<List<BlockOnExamRun>> getSeating() {
        return seating;
    }

    public void setSeating(List<List<BlockOnExamRun>> seating) {
        this.seating = seating;
    }
}
