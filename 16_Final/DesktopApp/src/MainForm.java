import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm
{
    private JPanel mainPanel;
    private JTextArea surnameText;
    private JTextArea nameText;
    private JButton collapseButton;
    private JLabel surname;
    private JLabel name;
    private JLabel patronymic;
    private JTextArea patronymicText;
    private JPanel firstPanel;
    private JTextArea snpText;
    private JButton expandButton;
    private JPanel secondPanel;

    public MainForm()
    {
        secondPanel.setVisible(false);

        collapseButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(surnameText.getText().length() > 0 && nameText.getText().length() > 0)
                {
                    firstPanel.setVisible(false);
                    secondPanel.setVisible(true);
                    snpText.setText(surnameText.getText() + " " + nameText.getText() + " " + patronymicText.getText());

                    surnameText.setText("");
                    nameText.setText("");
                    patronymicText.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            firstPanel,
                            "Вы указали не все данные.",
                            "Ошибка!",
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
            }
        });

        expandButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String[] arr = snpText.getText().split("\\s+");
                if(arr.length >= 2 && arr.length < 4)
                {
                    surnameText.setText(arr[0]);
                    nameText.setText(arr[1]);
                    if(arr.length == 3)
                        patronymicText.setText(arr[2]);

                    firstPanel.setVisible(true);
                    secondPanel.setVisible(false);

                    snpText.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(
                            firstPanel,
                            "Вы указали неверные данные.",
                            "Ошибка!",
                            JOptionPane.PLAIN_MESSAGE
                    );
                }
            }
        });
    }

    public JPanel getMainPanel()
    {
        return mainPanel;
    }
}
