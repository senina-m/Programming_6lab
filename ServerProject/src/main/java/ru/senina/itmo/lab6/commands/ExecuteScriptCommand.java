package ru.senina.itmo.lab6.commands;

@CommandAnnotation(name = "execute_script", filename = true)
public class ExecuteScriptCommand extends CommandWithoutArgs{
    private String filename;
    public ExecuteScriptCommand() {
        super("execute_script file_name", "read and execute the script from the specified file.");
    }
    @Override
    protected String doRun() {
        return "Execute script ";
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
