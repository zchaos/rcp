package rcptest.editor;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class SampleTextEditor extends EditorPart implements ISaveablePart2 {
	public static final String ID = "rcptest.editor.texteditor";
	private Text text;
	private boolean dirty;

	private FileInput input;

	@Override
	public int promptToSaveOnClose() {
		if (dirty) {
			if (MessageDialog.openConfirm(getEditorSite().getShell(), "提示", "内容未保存，确认继续关闭操作？")) {
				return ISaveablePart2.NO;
			}
			return ISaveablePart2.CANCEL;
		} else {
			return ISaveablePart2.YES;
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		saveToLocal(input.getName());
		dirty = false;
		firePropertyChange(ISaveablePart2.PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		FileDialog dialog = new FileDialog(getEditorSite().getShell(), SWT.SAVE);
		String path = dialog.open();
		if (path != null) {
			saveToLocal(path);
		}
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		setPartName(input.getName());
		this.input = (FileInput) input;
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	public void createPartControl(Composite parent) {
		text = new Text(parent, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.CANCEL | SWT.MULTI);
		loadText();
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				dirty = true;
				firePropertyChange(ISaveablePart2.PROP_DIRTY);
			}
		});
	}

	@Override
	public void setFocus() {
		text.setFocus();
	}

	private void loadText() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input.getName()),
					"utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = reader.readLine();
			}
			reader.close();
			text.setText(sb.toString());
		} catch (IOException e) {
		}
	}

	private void saveToLocal(String path) {
		try {
			OutputStream out = new FileOutputStream(path);
			OutputStreamWriter writer = new OutputStreamWriter(out, "utf-8");
			writer.write(text.getText());
			writer.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
