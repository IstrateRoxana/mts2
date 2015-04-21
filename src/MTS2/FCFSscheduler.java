package MTS2;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * jobs with no preemption 
 * @author raluca
 *
 */
public class FCFSscheduler implements Scheduler {

	private PriorityQueue<JobMessage> tasks;

	public FCFSscheduler() {
		this.init();
	}

	@Override
	public void init() {
		this.tasks = new PriorityQueue<JobMessage>(1, new Comparator<JobMessage>()
				{
			/**
			 * Sort jobs on timestamps
			 * Extend this class for different comparisons
			 */
			@Override
			public int compare(JobMessage o1, JobMessage o2) {
				if (o1.getTimestamp() < o2.getTimestamp())
				{
					return 1;
				}
				else if (o1.getTimestamp() > o2.getTimestamp())
				{
					return -1;
				}

				return 0;
			}
		});
	}

	@Override
	public void schedule(JobMessage job) {
		//by default, it can be scheduled
		tasks.add(job);
	}

	@Override
	public JobMessage processAt(long processingTimestamp) {
		if (tasks.isEmpty()) {
			return null;
		}
		
		//process a task
		JobMessage currentJob = tasks.remove();
		currentJob.setStartProcessingTimestamp(processingTimestamp);

		System.out.println("Processed job with length " + currentJob.getLength() + " at timestamp: " + processingTimestamp);
		currentJob.setFinishProcessingTimestamp(processingTimestamp + currentJob.getLength());

		//by default, no error when processing a task
		return currentJob;
	}

	@Override
	public long getLoading() {
		return tasks.size();
	}

	@Override
	public JobMessage deleteCurrentJob() {
		if (tasks.isEmpty()) {
			return null;
		}

		return tasks.remove();
	}
}
