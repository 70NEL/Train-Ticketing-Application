package GUI;

import bestroute.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JPanel panel1;
    private JPanel panelLogin;
    private JPasswordField pfPassword;
    private JTextField tfUser;
    private JLabel lbPass;
    private JLabel lbUser;
    private JButton btnLogin;
    private JPanel panelBooking;
    private JTable tbFrom;
    private JTable tbTo;
    private JLabel lbDepartureTime;
    private JTextField textField1;
    private JTextField textField2;
    private JButton btnSubmitBooking;
    private JLabel lbNumberOfTickets;
    private JTextField textField3;
    private JLabel lbMail;
    private JPanel panelManagement;
    private JPanel panelRoutes;
    private JPanel panelTrains;
    private JPanel panelBookings;
    private JPanel panelDelays;
    private JTextField tfFrom;
    private JTextField tfTo;
    private JComboBox cbRoutes;
    private JButton btnRoutesActions;
    private JLabel lbFrom;
    private JLabel lbTo;
    private JTabbedPane tpAdminPanel;
    private JPanel Admin;
    private JTabbedPane mainTabbedPane;
    private JTable tbRoutes;
    private JLabel lbOperation;
    private JButton btnSearch;
    private JLabel lbId;
    private JLabel lbBookedSeats;
    private JLabel lbNrOfSeats;
    private JLabel lbDelay;
    private JTextField tfIdTrains;
    private JTextField tfNrSeatsTrains;
    private JTextField tfBookedSeatsTrains;
    private JTextField tfDelayTrains;
    private JComboBox comboBox1;
    private JLabel lbSelOperationTrains;
    private JButton btnActionTrains;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JComboBox comboBox2;
    private JButton btnActionBookings;
    private JLabel lbFromBookings;
    private JLabel lbToBookings;
    private JLabel lbDepTime;
    private JLabel lbTicketsBookings;
    private JLabel lbMailBookings;
    private JLabel lbSelActionBookings;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JButton btnSendNot;
    private JLabel lbIdDelays;
    private JLabel lbToDelays;
    private JLabel lbFromDelays;

    public GUI() {
        RoutingService routingService = new RoutingService();
        List<Edge> initialEdges = routingService.getAllEdges();
        RouteTableModel routeModel = new RouteTableModel(initialEdges);
        TableRowSorter<RouteTableModel> sorter = new TableRowSorter<>(routeModel);
        tbRoutes.setModel(routeModel);
        tbRoutes.setRowSorter(sorter);

        btnSearch.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = tfFrom.getText();
                String to = tfTo.getText();

                List<RowFilter<Object, Object>> filters = new ArrayList<>();

                if(!from.isEmpty()) {
                    filters.add(RowFilter.regexFilter("(?i)" + from, 0));
                }
                if (!to.isEmpty()) {
                    filters.add(RowFilter.regexFilter("(?i)" + to, 1));
                }

                if (filters.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.andFilter(filters));
                }
            }
        });
        btnRoutesActions.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = cbRoutes.getSelectedItem().toString();
                String from = tfFrom.getText();
                String to = tfTo.getText();

                if(selectedOption.equals("Add")) {
                    if(from.isEmpty() || to.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please type the source and destination for your journey!");
                        return;
                    }

                    TrainRoutes.getInstance().addStation(from);
                    TrainRoutes.getInstance().addStation(to);
                    TrainRoutes.getInstance().createConnection(from, to, new Train(), "00:00", "00:00", 0.0);

                    routeModel.setNewData(routingService.getAllEdges());

                    tfFrom.setText("");
                    tfTo.setText("");
                    JOptionPane.showMessageDialog(null, "Route has been added!");
                }else if(selectedOption.equals("Delete")) {
                    int selectedRow = tbRoutes.getSelectedRow();

                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "Please select a route from the table to remove.");
                        return;
                    }

                    int modelRow = tbRoutes.convertRowIndexToModel(selectedRow);
                    Edge edgeToRemove = routeModel.getEdgeAt(modelRow);
                    TrainRoutes.getInstance().removeConnection(edgeToRemove);
                    routeModel.setNewData(routingService.getAllEdges());

                    JOptionPane.showMessageDialog(null, "Route removed successfully!");
                }
            }
        });
        btnActionTrains.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
