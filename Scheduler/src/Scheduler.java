//Note: Make and interface for scheduler
public abstract class Scheduler {
  Process currentProcess;
  ProcessList processList;
  Boolean preemptive = false;
  public String readableName;


  public Scheduler(ProcessList processList) {
    this.processList = processList;
    //Move all the processes into the ready queue to start;
    processList.reinitialize();

    this.currentProcess = this.getNextReadyProcess();
    System.out.print(currentProcess.getPID() + " > ");


  }

  public void schedule() {

    if (preemptive) {
      getNextReadyProcess();
      System.out.print(currentProcess.getPID() + " > ");
    } else {
      if (currentProcess.isTerminated() || currentProcess.isWaiting()) {
        currentProcess = getNextReadyProcess();
        System.out.print(currentProcess.getPID() + " > ");
      }
    }
    processList.decrementCurentProcessesWaiting();
  }

  public Process getCurrentProcess() {
    return currentProcess;
  }

  public abstract Process getNextReadyProcess();
}
