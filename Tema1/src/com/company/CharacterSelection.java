package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class CharacterSelection extends JFrame implements ListSelectionListener {
    JList<Character> list;
    //JTextField name, type, experience, level;
    DefaultListModel<Character> element;

    public CharacterSelection(List<Character> characters) {
        super("Choose Character:");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        element = new DefaultListModel<>();
        JScrollPane scrollPane = new JScrollPane();
        for (Character obj : characters) {
            element.addElement(obj);
        }
        list = new JList<>(element);
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        list.addListSelectionListener(this);
        add(scrollPane);
        setVisible(true);
        show();
        pack();
    }

    public void valueChanged(ListSelectionEvent e) {
        Character character = list.getSelectedValue();
    }
}
