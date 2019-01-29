package app.models;

public class DeviceInDiagnostics implements Comparable {
	//contains information about device and hes repair
	private Device device;
	private Repair repair;

	private int comparableId;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
	}

	public DeviceInDiagnostics(Device device, Repair repair) {
		this.device = device;
		this.repair = repair;
	}

	@Override
	public String toString() {
		return device.toString();
	}

	//todo implements sortable
	@Override
	public int compareTo(Object o) {
		int id = ((DeviceInDiagnostics)o).getDevice().getId();
		return this.comparableId - id;
	}
}
