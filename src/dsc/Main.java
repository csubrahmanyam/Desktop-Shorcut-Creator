package dsc;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static final String VERSION = "1.0.0";

    public static void main(String[] args) {
        
        String command;
        if(args.length == 0) {
            command = "about";
        } else {
            command = args[0];
        }

        userPrompt(command);
    }

    static void userPrompt(String command) {
        if (command.equals("about")) {
            System.out.println("dsc: Desktop Shortcut Creator");
            System.out.println("version: " + VERSION);
            System.out.println("\nSupported app formats:");
            System.out.println("  AppImages,");
            System.out.println("  Single binary apps,");
            System.out.println("  Single jar files");
            System.out.println("\nOn how to use, run:");
            System.out.println("  dsc help");
        } else if (command.equals("help")) {
            System.out.println("dsc commands list:");
            System.out.println("dsc         -gives about dsc");
            System.out.println("dsc help    -gives all commands list");
            System.out.println("dsc version -gives dsc's version");
            System.out.println("dsc new     -to create new app short-cut");
            System.out.println("dsc author  -gives about author of dsc");
        } else if (command.equals("version")) {
            System.out.println("dsc: Desktop Shortcut Creator");
            System.out.println("version: " + VERSION);
            
        } else if (command.equals("new")) {
            while(true) {
                Scanner input = new Scanner(System.in);
                ShortcutTemplate template = new ShortcutTemplate(); 
                
                System.out.println("Enter app name(for shortcut):");
                template.appName = input.nextLine();
                if(template.appName.equals("")) {
                    System.out.println("Enter all fields!");
                    continue;
                }

                System.out.println("Enter full path to app:");
                template.appPath = input.nextLine();
                if(template.appPath.equals("")) {
                    System.out.println("Enter all fields!");
                    continue;
                }

                System.out.println("Enter app type:");
                System.out.println(" enter 1 for AppImage");
                System.out.println(" enter 2 for binary file");
                System.out.println(" enter 3 for jar file");
                template.appType = input.nextLine();
                if(template.appType.equals("")) {
                    System.out.println("Enter all fields!");
                    continue;
                }else if (template.appType.equals("1") || template.appType.equals("2")) {
                    template.appPath = "gnome-terminal -- " + template.appPath;
                    
                }else if (template.appType.equals("3")) {
                    template.appPath = "java -jar " + template.appPath;
                    
                }

                System.out.println("Enter app type (cli or gui):");
                template.terminal = input.nextLine();
                if (template.terminal.equals("")) {
                    System.out.println("Enter all fields!");
                    continue;
                }else if (template.terminal.equals("cli")) {
                    template.terminal = "true";
                    
                }else {
                    template.terminal = "false";
                }

                System.out.println("Enter full path to icon (optional):");
                template.appIconPath = input.nextLine();
                if(template.appIconPath.equals("")) {
                    template.appIconPath = "utilities-terminal";
                }

                template.createShortcut();

                input.close();
                break;
            }
            
        }else if (command.equals("author")) {
            System.out.println("dsc Author: C Subrahmanyam");
            System.out.println("Credentials: Wrote 'hello world' program in 10 different languages!");
            
            
        }
        
    }

     
}

class ShortcutTemplate {
    String appName;
    String appPath;
    String appType;
    String appIconPath;
    String terminal;

    String content = "";


    void createShortcut() {
        content = "[Desktop Entry]";
        content += "\nType=Application";
        content += "\nTerminal=" + terminal;
        content += "\nExec=" + appPath;
        content += "\nName=" + appName;
        content += "\nIcon=" + appIconPath;
        try {
            String userName = System.getProperty("user.name");
            File appShortcut = new File("/home/"+userName+"/.local/share/applications/"+appName+".desktop");
            if (appShortcut.createNewFile()) {
                System.out.println("Creting shorcut...");
                appShortcut.setExecutable(true);
            } else {
                System.out.println("A shortcut with name " + appName + " already exists!");
                System.out.println("Try another name!");
                System.exit(0);
            }
            FileWriter appShortcutWriter = new FileWriter(appShortcut);
            appShortcutWriter.write(content);
            appShortcutWriter.close();
            System.out.println("Successfully Created Shortcut!\n");
            System.out.println("Now follow the steps:");
            System.out.println(" Go to Linux Menu");
            System.out.println(" Search for name '" + appName +"'");
            System.out.println(" Right click on the name in results and");
            System.out.println(" select 'add to desktop or panel'!");
            System.out.println(" Yep, now see your panel or dekstop for your shortcut!");


        } catch (IOException e) {
            System.out.println("An error occured!");
            e.printStackTrace();    
        }
    }
}