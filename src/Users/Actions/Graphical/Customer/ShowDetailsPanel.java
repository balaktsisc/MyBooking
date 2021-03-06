package Users.Actions.Graphical.Customer;

import Users.Actions.Graphical.AdjustSize;
import Users.Actions.Graphical.UserDetailsPanel;
import Users.Customer;

import javax.swing.*;

/**
 * This is a child of class UserDetails, adding to the user detail
 * panel some extra information fields that only apply to Customers.
 * @author Christos Balaktsis
 */

public class ShowDetailsPanel extends UserDetailsPanel {

    private final JTextField addressField = new JTextField();
    private Customer customer;

    @Override
    protected void invoke() {
        super.invoke();
        customer = (Customer) parentUser;
        JLabel addressLabel = new JLabel();

        //---- addressField ----
        addressLabel.setText("Address");
        mainPanel.add(addressLabel);
        addressLabel.setBounds(20, 225, 100, 20);

        //---- addressField ----
        addressField.setText(((Customer) parentUser).getAddress());
        mainPanel.add(addressField);
        addressField.setBounds(140, 225, 200, 20);

        AdjustSize.AdjustPanelSize(mainPanel);
    }

    @Override
    protected void changeOthers() {
        customer = (Customer) parentUser;
        customer.setAddress(addressField.getText().length()!=0 ? addressField.getText() : customer.getAddress());
    }
}
