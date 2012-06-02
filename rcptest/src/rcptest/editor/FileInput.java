package rcptest.editor;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class FileInput implements IEditorInput {
	private String path = "";

	public FileInput(String path) {
		this.path = path;
	}

	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists() {
		return new File(path).exists();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return path;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		return path;
	}

	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj instanceof FileInput) {
			return path.equals(((FileInput) obj).getName());
		}
		return false;
	}

	public int hashCode() {
		return path.hashCode();
	}
}
