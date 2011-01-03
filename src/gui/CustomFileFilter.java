package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.*;

import definition.Action;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class CustomFileFilter extends FileFilter {

	public List<String> enableExtension;
	
	public CustomFileFilter(List<String> penableExtension){
		super();
		enableExtension = penableExtension;
	}
	
	public CustomFileFilter(String penableExtension){
		super();
		enableExtension =  new ArrayList<String>();
		enableExtension.add(penableExtension);		
	}
	
	
    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String extension = getExtension(f);
        return enableExtension.contains(extension);
    }
    
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    //The description of this filter
    public String getDescription() {
        return "Just Files of type "+enableExtension.toString();
    }
}
