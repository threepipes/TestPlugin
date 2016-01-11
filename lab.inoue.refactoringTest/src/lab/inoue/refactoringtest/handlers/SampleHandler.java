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
		// ワークベンチの取得．ここからワークベンチ内のほとんどの情報が取得できる
		IWorkbench workbench = PlatformUI.getWorkbench();
		// アクティブになっているウィンドウ(フォーカスがあっているウィンドウ)を取得
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		// アクティブになっているパースペクティブを取得
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		// アクティブになっているビューを取得
		IWorkbenchPart workbenchPart = workbenchPage.getActivePart();
		// アクティブになっているエディタを取得
		// エディタとファイルは１対１であり，エディタのリストはworkbenchPageから取得する構造になっている
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		// エディタ内で開かれているファイルに関する情報を取得
		IEditorInput editorInput = editorPart.getEditorInput();
		// エディタ内で開かれているファイル名を取得
		String fileName = editorInput.getName();
		// エディタ内部に関する情報を取得
		AbstractTextEditor abstractTextEditor = (AbstractTextEditor) editorPart;
		// エディタで開かれているファイルの文字列情報を取得
		IDocument document = abstractTextEditor.getDocumentProvider().getDocument(editorInput);
		// ファイルの内容を文字列で取得
		String source = document.get();
		// エディタ内のテキスト選択の範囲を取得
		ITextEditor textEditor = abstractTextEditor;
		ISelectionProvider selectionProvider = textEditor.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		ITextSelection textSelection = (ITextSelection) selection;
		// エディタの内容を文字列と見たときのカーソルの位置を取得
		int offset = textSelection.getOffset();


		
		/* Refactoringインスタンスの生成 */
		Refactoring refactoring = createRefactoring();

		/* Refactoringインスタンスを用いてCheckConditionsOparationインスタンスを生成 */
		CheckConditionsOperation checkOP
		= new CheckConditionsOperation(refactoring, CheckConditionsOperation.ALL_CONDITIONS);

		/* 条件チェックを実行 */
		try {
			checkOP.run(new NullProgressMonitor());

			/* Refactoringインスタンスを用いてCreateChangeOparationインスタンスを生成 */
			CreateChangeOperation changeOP = new CreateChangeOperation(refactoring);

			/* Changeの生成を実行 */
			changeOP.run(new NullProgressMonitor());

			/* 生成されたChangeインスタンスを取得 */
			Change change = changeOP.getChange();

			/* Changeインスタンスを用いてPerformChangeOperationインスタンスを生成 */
			PerformChangeOperation op = new PerformChangeOperation(change);

			/* ソースコードにChangeを適用する */
			op.run(new NullProgressMonitor());

		} catch (CoreException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return null;
	}

	public Refactoring createRefactoring(){
		return new Refactoring() {
			
			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "Test refactoring";
			}
			
			@Override
			public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}
			
			@Override
			public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
					throws CoreException, OperationCanceledException {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}
			
			@Override
			public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
					throws CoreException, OperationCanceledException {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}
		};
	}
}
