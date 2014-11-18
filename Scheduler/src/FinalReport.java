import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FinalReport {
	public CPU cpu; // PI ref to the CPU
	// PI whole bunch of stats
	public ArrayList<Integer> executionOrder;
	public double throughput;
	public int turnAroundTime;
	public double CPUUtilization;
	
	private int lastAddedPID = -1;
	
	public int deadlineViolations; // PI deadline violations
	
	public FinalReport(CPU cpu) {
		this.executionOrder = new ArrayList<Integer>();
		this.cpu = cpu;
		throughput = 0;
		turnAroundTime = 0;
		CPUUtilization = 0;
		deadlineViolations = -1; // PI some schedulers do not report this
	}

	/**
	 * PI method that adds the process to the execution order - does not add sequential duplicates
	 * @param process process to add
	 */
	public void addProcess(Process process) {
		if(process != null) {
			executionOrder.add(process.getPID());
		}
	}

	public void printFinalReport() {
		try {
			FileWriter fileWriter = new FileWriter("FinalReport.txt", true); // PI make a new file writer
			// PI now output all the junk needed
			fileWriter.write("==================================================\n");
			fileWriter.write("Final report for "+this.cpu.scheduler.readableName+"\n");
			fileWriter.write("CPU Execution order for "+this.cpu.scheduler.readableName+"\n");
			int outputCount = 0;
			for(Integer pid: executionOrder) {
				if(outputCount == 0) { // PI is this the first line?
					fileWriter.write("\t"); // PI put in a tab for format
				}
				fileWriter.write("PID"+pid+" >> ");
				outputCount++;
				if(outputCount >= 6) {
					outputCount = 0;
					fileWriter.write("\n");
				}
			}
			fileWriter.write("Done\n");
			// PI let's do some data cals
			if(this.cpu.idleCycles == 0) {
				this.CPUUtilization = 100;
			} else {
				this.CPUUtilization = 100 - (this.cpu.busyCycles/this.cpu.idleCycles);
			}
			this.turnAroundTime = (int) this.cpu.busyCycles;
			fileWriter.write("Throughput for "+this.cpu.scheduler.readableName+" = "+this.throughput+"\n");
			fileWriter.write("Total Turn-around Time for "+this.cpu.scheduler.readableName+" = "+this.turnAroundTime+"\n");
			fileWriter.write("Average Wait Time for "+this.cpu.scheduler.readableName+" TODO\n");
			fileWriter.write("CPU Utilization for "+this.cpu.scheduler.readableName+" = "+this.CPUUtilization+"%\n");
			fileWriter.write("\n");
			fileWriter.close(); // PI close the file writer
		} catch (IOException e) {
			// uh oh
			e.printStackTrace();
		}
	}
}
