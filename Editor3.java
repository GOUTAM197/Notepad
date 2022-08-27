import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
class Editor3 extends WindowAdapter implements ActionListener, ItemListener {
  Frame f, inner_frame;
  MenuBar mb;
  Menu m1, m2, m3;
  MenuItem nw, opn, ext, sve, rep, ct, cpy, pst, save, findnext;
  CheckboxMenuItem bld, itlc;
  TextArea t;
  TextField t2, t3;
  FileDialog fd;
  FileOutputStream fos;
  FileInputStream fis;
  String file_name = "";
  String data = "";
  int ch, style = 0;
  int index = 0;
  Button b1, b2, b3, b4, yes, no, y, n, yy, nn, yes1;
  Panel p;
  Label l;
  String regex, input, dum = "";
  Pattern pat;
  Matcher mat;
  int x = 0;
  int m = 0;
  int mn = 0;

  public Editor3() {
    f = new Frame();
    f.setSize(1700, 900);
    t = new TextArea();
    f.add(t);
    f.addWindowListener(this);
    /*f.addFocusListener(new FocusListener(){
    public void focusGained(FocusEvent e)
    {
    t.requestFocus();
    }
    public void focusLost(FocusEvent e)
    {
    }
      });*/

    mb = new MenuBar();
    m1 = new Menu("File");
    m2 = new Menu("Edit");
    m3 = new Menu("Others");

    l = new Label("Do you want to save your file..?");

    nw = new MenuItem("New");
    nw.addActionListener(this);
    opn = new MenuItem("Open");
    opn.addActionListener(this);
    sve = new MenuItem("Save as");
    sve.addActionListener(this);
    ext = new MenuItem("Exit");
    ext.addActionListener(this);
    rep = new MenuItem("Replace");
    rep.addActionListener(this);
    ct = new MenuItem("Cut");
    cpy = new MenuItem("Copy");
    pst = new MenuItem("Paste");
    bld = new CheckboxMenuItem("Bold");
    bld.addItemListener(this);
    itlc = new CheckboxMenuItem("Italic");
    itlc.addItemListener(this);
    save = new MenuItem("Save");
    save.addActionListener(this);
    findnext = new MenuItem("find next");
    findnext.addActionListener(this);
    //itlc.setState(true);

    mb.add(m1);
    mb.add(m2);
    mb.add(m3);
    m1.add(nw);
    m1.add(opn);
    m1.add(save);
    m1.add(sve);
    m1.addSeparator();
    m1.add(ext);
    m2.add(bld);
    m2.add(itlc);
    m3.add(ct);
    m3.add(cpy);
    m3.add(pst);
    m3.add(findnext);
    m3.add(rep);
    f.setMenuBar(mb);
    f.setVisible(true);

  }

  public void itemStateChanged(ItemEvent e) {
    if ((e.getSource() == bld) && (bld.getState() == true) && (itlc.getState() == false)) {
      Font f = new Font("Arial", Font.BOLD, 14);
      t.setFont(f);
      /*else
      {
      style--;
      Font f=new Font("Lucida",Font.REGULAR,10);
      t.setFont(f);
      }*/
    }
    if ((e.getSource() == itlc) && (itlc.getState() == true) && (bld.getState() == false))

    {
      Font f = new Font("Arial", Font.ITALIC, 14);
      t.setFont(f);
    }

    if ((e.getSource() == bld) && (bld.getState() == true) && (itlc.getState() == true)) {
      Font f = new Font("Arial", Font.BOLD + Font.ITALIC, 14);
      t.setFont(f);
    }
    if ((e.getSource() == itlc) && (bld.getState() == true) && (itlc.getState() == true)) {
      Font f = new Font("Arial", Font.BOLD + Font.ITALIC, 14);
      t.setFont(f);
    }

  }
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == yes1) {
      fd = new FileDialog(f, "SAVE", FileDialog.SAVE);
      fd.setVisible(true);
      file_name = fd.getFile();
      try {
        fos = new FileOutputStream(file_name);
        data = t.getText();
        for (int i = 0; i < data.length(); i++) {
          ch = data.charAt(i);
          fos.write(ch);
        }
        fos.close();
      } catch (IOException io) {
        System.out.print(io.getMessage());
      }
    }
    if (e.getSource() == findnext) {
      inner_frame = new Frame();
      inner_frame.setSize(300, 100);
      inner_frame.setVisible(true);
      p = new Panel();
      inner_frame.add(p);
      t2 = new TextField("Find next");
      b4 = new Button("Find next");
      p.add(t2);
      p.add(b4);
      b4.addActionListener(this);

      inner_frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          Window w = e.getWindow();
          w.setVisible(false);
          w.dispose();
          f.setVisible(true);
          f.setEnabled(true);
          data = "";
        }
      });
    }
    if (e.getSource() == b4) {
      regex = t2.getText();
      input = t.getText();
      pat = Pattern.compile(regex);
      mat = pat.matcher(input);

      if (mat.find(index)) {
        t.select(mat.start() - mn, mat.end());
        index = t.getSelectionEnd();
        mn++;
      }
    }

    if (e.getSource() == ext) {
      if (!data.equals(t.getText())) {
        inner_frame = new Frame();
        inner_frame.setSize(400, 100);
        Panel p = new Panel();
        p.add(l);
        yes = new Button("Yes");
        yes.addActionListener(this);
        no = new Button("No");
        no.addActionListener(this);
        p.add(yes);
        p.add(no);
        inner_frame.add(p);
        inner_frame.setVisible(true);
        inner_frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Window w = e.getWindow();
            w.setVisible(false);
            w.dispose();
            f.setVisible(true);
            f.setEnabled(true);
            data = "";
          }
        });

      } else {
        System.exit(1);
      }
    }
    if (e.getSource() == save) {
      if (file_name.equals("")) {
        fd = new FileDialog(f, "SAVE", FileDialog.SAVE);
        fd.setVisible(true);
        file_name = fd.getFile();
        try {
          fos = new FileOutputStream(file_name);
          data = t.getText();
          for (int i = 0; i < data.length(); i++) {
            ch = data.charAt(i);
            fos.write(ch);
          }
          fos.close();
        } catch (IOException io) {
          System.out.print(io.getMessage());
        }

      } else {
        try {
          fos = new FileOutputStream(file_name);
          data = t.getText();
          for (int i = 0; i < data.length(); i++) {
            ch = data.charAt(i);
            fos.write(ch);
          }
          fos.close();
          data = t.getText();
        } catch (IOException io) {
          System.out.print(io.getMessage());
        }
      }
    }
    if (e.getSource() == no) {
      System.exit(1);
    }
    if (e.getSource() == yes) {
      inner_frame.setVisible(false);
      if (file_name.equals("")) {
        fd = new FileDialog(f, "SAVE", FileDialog.SAVE);
        fd.setVisible(true);
        file_name = fd.getFile();
        try {
          fos = new FileOutputStream(file_name);
          data = t.getText();
          for (int i = 0; i < data.length(); i++) {
            ch = data.charAt(i);
            fos.write(ch);
          }
          fos.close();
          System.exit(1);
        } catch (IOException io) {
          System.out.print(io.getMessage());
        }
      } else {
        try {
          data = t.getText();
          fos = new FileOutputStream(file_name);
          int i = 0;
          while (i != data.length()) {
            ch = data.charAt(i);
            fos.write(ch);
            i++;
          }
          fos.close();
          inner_frame.setVisible(false);
          System.exit(1);
        } catch (IOException E) {
          System.out.print(E.getMessage());
        }
      }
    }

    if (e.getSource() == rep) {
      inner_frame = new Frame();
      inner_frame.setSize(400, 100);
      inner_frame.setVisible(true);
      Panel p = new Panel();
      inner_frame.add(p);
      b1 = new Button("replace");
      b2 = new Button("replace all");
      b3 = new Button("find");
      t2 = new TextField("find");
      t3 = new TextField("replace");
      b1.addActionListener(this);
      b2.addActionListener(this);
      b3.addActionListener(this);
      p.add(t2);
      p.add(t3);
      p.add(b1);
      p.add(b2);
      p.add(b3);
      inner_frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          Window w = e.getWindow();
          w.setVisible(false);
          w.dispose();
          f.setVisible(true);
          f.setEnabled(true);

        }
      });

    }
    if (e.getSource() == b3) {
      regex = t2.getText();
      input = t.getText();
      pat = Pattern.compile(regex);
      mat = pat.matcher(input);

      if (mat.find(index)) {

        t.select(mat.start() - m, mat.end());
        index = t.getSelectionEnd();
        m++;
      }
    }
    if (e.getSource() == b2) {
      regex = t2.getText();
      input = t.getText();
      Pattern patt = Pattern.compile(regex);
      Matcher match = patt.matcher(input);
      while (match.find()) {
        t.setText(match.replaceAll(t3.getText()));
        //t.setText(input);
      }

    }
    if (e.getSource() == b1) {
      regex = t2.getText();
      input = t.getText();
      Pattern patt = Pattern.compile(regex);
      Matcher match = patt.matcher(input);
      if (t.getSelectionStart() == t.getSelectionEnd()) {
        t.setText(match.replaceFirst(t3.getText()));
      } else {
        match.find(t.getSelectionStart());
        StringBuffer xyz = new StringBuffer(t.getText());
        xyz.replace(match.start(), match.end(), t3.getText());
        t.setText(xyz.toString());
	   t.select(0,0);
        //t.setText(input);
      }
    }

    if (e.getSource() == sve) {
      fd = new FileDialog(f, "SAVE", FileDialog.SAVE);
      fd.setVisible(true);
      file_name = fd.getFile();
      try {
        fos = new FileOutputStream(file_name);
        data = t.getText();
        for (int i = 0; i < data.length(); i++) {
          ch = data.charAt(i);
          fos.write(ch);
        }
        fos.close();

      } catch (IOException io) {
        System.out.print(io.getMessage());
      }
    }

    if ((e.getActionCommand().equals("New"))) {

      if (!data.equals(t.getText())) {
        inner_frame = new Frame();
        inner_frame.setSize(400, 100);
        Panel p = new Panel();
        p.add(l);
        y = new Button("Yes");
        y.addActionListener(this);
        n = new Button("No");
        n.addActionListener(this);
        p.add(y);
        p.add(n);
        inner_frame.add(p);
        inner_frame.setVisible(true);
        inner_frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Window w = e.getWindow();
            w.setVisible(false);
            w.dispose();
            f.setVisible(true);
            f.setEnabled(true);
            data = "";
          }
        });
      } else {
        t.setText("");
      }
    }
    if (e.getSource() == y) {
      inner_frame.setVisible(false);
      fd = new FileDialog(f, "SAVE", FileDialog.SAVE);
      fd.setVisible(true);
      file_name = fd.getFile();
      data = t.getText();
      try {
        fos = new FileOutputStream(file_name);
        for (int i = 0; i < data.length(); i++) {
          ch = data.charAt(i);
          fos.write(ch);
        }
        fos.close();
        t.setText("");
      } catch (IOException io) {
        System.out.print(io.getMessage());
      }

    }
    if (e.getSource() == n) {
      inner_frame.setVisible(false);
      t.setText("");
    }

    if ((e.getActionCommand().equals("Open"))) {
      if ((t.getText()).equals("")) {
        try {
          fd = new FileDialog(f, "OPEN", FileDialog.LOAD);
          fd.setVisible(true);
          file_name = fd.getFile();
          fis = new FileInputStream(file_name);
          t.setText("");
          while ((ch = fis.read()) != -1) {
            dum = dum + "" + (char) ch;

          }
          t.setText(t.getText() + "" + dum);
          fis.close();
          dum = "";
        } catch (IOException io) {
          System.out.print(io.getMessage());
        }
      }

      if (!data.equals(t.getText())) {
        inner_frame = new Frame();
        inner_frame.setSize(400, 100);
        Panel p = new Panel();
        p.add(l);
        yy = new Button("Yes");
        yy.addActionListener(this);
        nn = new Button("No");
        nn.addActionListener(this);
        p.add(yy);
        p.add(nn);
        inner_frame.add(p);
        inner_frame.setVisible(true);
        data = t.getText();
        inner_frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            Window w = e.getWindow();
            w.setVisible(false);
            w.dispose();
            f.setVisible(true);
            f.setEnabled(true);
            data = "";
          }
        });
      } else {
        try {
          fd = new FileDialog(f, "OPEN", FileDialog.LOAD);
          fd.setVisible(true);
          file_name = fd.getFile();
          fis = new FileInputStream(file_name);
          t.setText("");
          while ((ch = fis.read()) != -1) {
            dum = dum + "" + (char) ch;

          }
          t.setText(t.getText() + dum);
          fis.close();
          data = t.getText();
        } catch (IOException io) {
          System.out.print(io.getMessage());
        }
        dum = "";
      }
    }
    if (e.getSource() == yy) {

      try {
        inner_frame.setVisible(false);
        fd = new FileDialog(f, "SAVE", FileDialog.SAVE);
        fd.setVisible(true);
        file_name = fd.getFile();
        fos = new FileOutputStream(file_name);
        for (int i = 0; i < data.length(); i++) {
          ch = data.charAt(i);
          fos.write(ch);
        }
        fos.close();
        fd = new FileDialog(f, "OPEN", FileDialog.LOAD);
        fd.setVisible(true);
        file_name = fd.getFile();
        fis = new FileInputStream(file_name);
        t.setText("");
        while ((ch = fis.read()) != -1) {
          dum = dum + "" + (char) ch;

        }
        t.setText(t.getText() + "" + dum);
        fis.close();
        data = t.getText();
        dum = "";
      } catch (IOException io) {
        System.out.print(io.getMessage());
      }
    }
    if (e.getSource() == nn) {
      inner_frame.setVisible(false);
      try {
        fd = new FileDialog(f, "OPEN", FileDialog.LOAD);
        fd.setVisible(true);
        file_name = fd.getFile();
        fis = new FileInputStream(file_name);
        t.setText("");
        while ((ch = fis.read()) != -1) {
          dum = dum + "" + (char) ch;

        }
        t.setText(t.getText() + "" + dum);
        fis.close();
        dum = "";
      } catch (IOException io) {
        System.out.print(io.getMessage());
      }
    }
    //data="";

  }

  public void windowClosing(WindowEvent we) {

    if (!data.equals(t.getText())) {
      inner_frame = new Frame();
      inner_frame.setSize(400, 100);
      Panel p = new Panel();
      p.add(l);
      yes1 = new Button("Yes");
      yes1.addActionListener(this);
      no = new Button("No");
      no.addActionListener(this);
      p.add(yes1);
      p.add(no);
      inner_frame.add(p);
      inner_frame.setVisible(true);
      inner_frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          Window w = e.getWindow();
          w.setVisible(false);
          w.dispose();
          f.setVisible(true);
          f.setEnabled(true);
        }
      });

    } else {
      Window w = we.getWindow();
      w.setVisible(false);
      w.dispose();
      System.exit(1);
    }

  }
  public static void main(String args[]) {
    Editor3 e = new Editor3();

  }

}