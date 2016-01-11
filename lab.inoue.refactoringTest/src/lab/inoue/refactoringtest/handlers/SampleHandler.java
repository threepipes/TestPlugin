package lab.inoue.refactoringtest.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.internal.corext.refactoring.scripting.MoveMethodRefactoringContribution;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.CreateChangeOperation;
import org.eclipse.ltk.core.refactoring.PerformChangeOperation;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// ���[�N�x���`�̎擾�D�������烏�[�N�x���`���̂قƂ�ǂ̏�񂪎擾�ł���
		IWorkbench workbench = PlatformUI.getWorkbench();
		// �A�N�e�B�u�ɂȂ��Ă���E�B���h�E(�t�H�[�J�X�������Ă���E�B���h�E)���擾
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		// �A�N�e�B�u�ɂȂ��Ă���p�[�X�y�N�e�B�u���擾
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		// �A�N�e�B�u�ɂȂ��Ă���r���[���擾
		IWorkbenchPart workbenchPart = workbenchPage.getActivePart();
		// �A�N�e�B�u�ɂȂ��Ă���G�f�B�^���擾
		// �G�f�B�^�ƃt�@�C���͂P�΂P�ł���C�G�f�B�^�̃��X�g��workbenchPage����擾����\���ɂȂ��Ă���
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		// �G�f�B�^���ŊJ����Ă���t�@�C���Ɋւ�������擾
		IEditorInput editorInput = editorPart.getEditorInput();
		// �G�f�B�^���ŊJ����Ă���t�@�C�������擾
		String fileName = editorInput.getName();
		// �G�f�B�^�����Ɋւ�������擾
		AbstractTextEditor abstractTextEditor = (AbstractTextEditor) editorPart;
		// �G�f�B�^�ŊJ����Ă���t�@�C���̕���������擾
		IDocument document = abstractTextEditor.getDocumentProvider().getDocument(editorInput);
		// �t�@�C���̓��e�𕶎���Ŏ擾
		String source = document.get();
		// �G�f�B�^���̃e�L�X�g�I���͈̔͂��擾
		ITextEditor textEditor = abstractTextEditor;
		ISelectionProvider selectionProvider = textEditor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		ITextSelection textSelection = (ITextSelection) selection;
		// �G�f�B�^�̓��e�𕶎���ƌ����Ƃ��̃J�[�\���̈ʒu���擾
		int offset = textSelection.getOffset();


		
		/* Refactoring�C���X�^���X�̐��� */
		Refactoring refactoring = createRefactoring();

		/* Refactoring�C���X�^���X��p����CheckConditionsOparation�C���X�^���X�𐶐� */
		CheckConditionsOperation checkOP
		= new CheckConditionsOperation(refactoring, CheckConditionsOperation.ALL_CONDITIONS);

		/* �����`�F�b�N�����s */
		try {
			checkOP.run(new NullProgressMonitor());

			/* Refactoring�C���X�^���X��p����CreateChangeOparation�C���X�^���X�𐶐� */
			CreateChangeOperation changeOP = new CreateChangeOperation(refactoring);

			/* Change�̐��������s */
			changeOP.run(new NullProgressMonitor());

			/* �������ꂽChange�C���X�^���X���擾 */
			Change change = changeOP.getChange();

			/* Change�C���X�^���X��p����PerformChangeOperation�C���X�^���X�𐶐� */
			PerformChangeOperation op = new PerformChangeOperation(change);

			/* �\�[�X�R�[�h��Change��K�p���� */
			op.run(new NullProgressMonitor());

		} catch (CoreException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}

		return null;
	}

	public Refactoring createRefactoring(){
		return new Refactoring() {
			
			@Override
			public String getName() {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				return "Test refactoring";
			}
			
			@Override
			public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				return null;
			}
			
			@Override
			public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
					throws CoreException, OperationCanceledException {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				return null;
			}
			
			@Override
			public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
					throws CoreException, OperationCanceledException {
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				return null;
			}
		};
	}
}
