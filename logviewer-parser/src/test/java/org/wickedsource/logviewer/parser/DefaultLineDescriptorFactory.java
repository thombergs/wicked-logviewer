package org.wickedsource.logviewer.parser;

import org.wickedsource.logviewer.parser.descriptor.CustomFieldDescriptor;
import org.wickedsource.logviewer.parser.descriptor.LineDescriptor;

public class DefaultLineDescriptorFactory {

	public LineDescriptor createLogFileDescriptor() {
		LineDescriptor descriptor = new LineDescriptor();
		descriptor.setLineRegex("^([0-9]+-[0-9]+-[0-9]+ [0-9]+:[0-9]+:[0-9]+,[0-9]+) *([^ ]*) *([^ ]*) *([^ ]*) *(.*)$");
		descriptor.setDateGroup(1);
		descriptor.setLoggerGroup(3);
		descriptor.setSeverityGroup(2);
		descriptor.setMessageGroup(5);
		descriptor.setDatePattern("yyyy-MM-dd HH:mm:ss,S");

		CustomFieldDescriptor threadDescriptor = new CustomFieldDescriptor();
		threadDescriptor.setFieldGroup(4);
		threadDescriptor.setFieldTitle("Thread");
		descriptor.getCustomFieldDescriptors().add(threadDescriptor);

		return descriptor;
	}

}
