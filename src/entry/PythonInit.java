package entry;

import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import program.SafeGetAttr;
import program.SafeImport;

public class PythonInit
{
	public static void init()
	{
		PythonInterpreter temp = new PythonInterpreter();
		temp.exec("pass");
		sanitize(temp);
		temp.close();
	}
	
	public static void sanitize(PythonInterpreter interpreter)
	{
		try
		{
			PyObject locals = interpreter.getLocals();
			PyObject builtins = locals.__getitem__(new PyString("__builtins__"));
			//things that have no reason to exist and will never be seen again
			builtins.__delattr__("exit");
			builtins.__delattr__("eval");
			builtins.__delattr__("execfile");
			builtins.__delattr__("compile");
			builtins.__delattr__("reload");
			
			//things which will one day be replaced
			builtins.__delattr__("raw_input");
			builtins.__delattr__("print");
			builtins.__delattr__("open");
			builtins.__delattr__("file");
			
			
			PyObject unsafeImport = builtins.__getattr__(new PyString("__import__"));
			builtins.__setattr__("__import__", new SafeImport(unsafeImport));
			builtins.__setattr__("getattr",new SafeGetAttr(builtins.__getattr__("getattr")));
			builtins.__setattr__("setattr",new SafeGetAttr(builtins.__getattr__("setattr")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
