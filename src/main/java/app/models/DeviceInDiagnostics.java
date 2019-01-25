package app.models;

public class DeviceInDiagnostics {
	//contains information about device and hes repair
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

	public DeviceInDiagnostics(Device device, Repair repair) {
		this.device = device;
		this.repair = repair;
	}
}
