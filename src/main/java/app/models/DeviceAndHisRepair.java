package app.models;

public class DeviceAndHisRepair implements Comparable {
	//contains information about device and his repair
	private Device device;
	private Repair repair;

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

	public DeviceAndHisRepair(Device device, Repair repair) {
		this.device = device;
		this.repair = repair;
	}

	public DeviceAndHisRepair() {
		this.device = new Device();
		this.repair = new Repair();
	}

	@Override
	public String toString() {
		return device.toString();
	}

	@Override
	public int compareTo(Object o) {
		int id = ((DeviceAndHisRepair)o).getDevice().getId();
//		return device.getId() + id;
		return device.getId() - id;
	}
}
