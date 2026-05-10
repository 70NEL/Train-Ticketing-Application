package GUI;

import bestroute.*;
import businesslogic.Booking;

import java.awt.event.ComponentAdapter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {
    public JPanel panel1;
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
    private JTextField tfDepTimeBooking;
    private JTextField tfTicketNoBooking;
    private JButton btnSubmitBooking;
    private JLabel lbNumberOfTickets;
    private JTextField tfMail;
    private JLabel lbMail;
    private JPanel panelManagement;
    private JPanel panelRoutes;
    private JPanel panelTrains;
    private JPanel panelBookings;
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
    private JComboBox cbOperationTrains;
    private JLabel lbSelOperationTrains;
    private JButton btnActionTrains;
    private JComboBox cbSelectTrain;
    private JButton btnActionBookings;
    private JLabel lbSelectTrain;
    private JTable tbBookings;
    private JTextField tfIdRoutes;
    private JLabel lbIdRoutes;

    private void updateTrainComboBox() {
        cbSelectTrain.removeAllItems();
        Collection<Train> allTrains = TrainRoutes.getInstance().getAllTrains();

        for(Train t : allTrains) {
            cbSelectTrain.addItem(String.valueOf(t.getId()));
        }
    }

    private List<Node> tableForStations(String fromOrTo) {
        RoutingService routingService = new RoutingService();
        List<Edge> allEdges = routingService.getAllEdges();
        Set<Node> nodes = new LinkedHashSet<>();
        for(Edge edge : allEdges) {
            if(fromOrTo.equals("from")) {
                nodes.add(edge.getFrom());
            }else if(fromOrTo.equals("to")) {
                nodes.add(edge.getTo());
            }
        }

        return new ArrayList<>(nodes);
    }

    private void bookingTablesInit() {
        String[] collumnFrom = {"Departure Station"};
        String[] collumnTo = {"Arrival Station"};
        DefaultTableModel modelFrom = new DefaultTableModel(collumnFrom, 0);
        DefaultTableModel modelTo = new DefaultTableModel(collumnTo, 0);

        List<Node> fromNodes = tableForStations("from");
        List<Node> toNodes = tableForStations("to");

        for(Node fromNode : fromNodes) {
            Object[] row = {
                    fromNode.getName()
            };
            modelFrom.addRow(row);
        }

        for(Node toNode : toNodes) {
            Object[] row = {
                    toNode.getName()
            };
            modelTo.addRow(row);
        }

        tbFrom.setModel(modelFrom);
        tbTo.setModel(modelTo);
    }

    public void initTestData() {
        TrainRoutes tr = TrainRoutes.getInstance();

        tr.addStation("Cluj-Napoca");
        tr.addStation("Zalau");
        tr.addStation("Bucuresti");
        tr.addStation("Brasov");
        tr.addStation("Iasi");
        tr.addStation("Timisoara");

        Train t101 = new Train();t101.setId(101);t101.setNrSeats(120);
        Train t202 = new Train();t202.setId(202);t202.setNrSeats(80);
        Train t303 = new Train();t303.setId(303);t303.setNrSeats(200);
        Train t404 = new Train();t404.setId(404);t404.setNrSeats(150);

        tr.addOrUpdateTrain(t101);
        tr.addOrUpdateTrain(t202);
        tr.addOrUpdateTrain(t303);
        tr.addOrUpdateTrain(t404);

        tr.createConnection("Cluj-Napoca", "Zalau", t101, "08:00", "09:30", 80.0);
        tr.createConnection("Zalau", "Cluj-Napoca", t101, "10:00", "11:30", 80.0);
        tr.createConnection("Bucuresti", "Brasov", t202, "07:00", "09:45", 166.0);
        tr.createConnection("Brasov", "Bucuresti", t202, "16:00", "18:45", 166.0);
        tr.createConnection("Cluj-Napoca", "Bucuresti", t303, "06:00", "14:00", 450.0);
        tr.createConnection("Bucuresti", "Iasi", t404, "12:00", "19:00", 390.0);
        tr.createConnection("Timisoara", "Cluj-Napoca", t202, "05:00", "10:00", 320.0);
        tr.createConnection("Iasi", "Bucuresti", t404, "08:00", "15:00", 390.0);
    }

    public GUI() {
        RoutingService routingService = new RoutingService();
        List<Edge> initialEdges = routingService.getAllEdges();
        RouteTableModel routeModel = new RouteTableModel(initialEdges);
        TableRowSorter<RouteTableModel> sorter = new TableRowSorter<>(routeModel);
        routeModel.setNewData(initialEdges);
        tbRoutes.setModel(routeModel);
        tbRoutes.setRowSorter(sorter);
        panelManagement.setVisible(false);
        panelLogin.setVisible(true);
        initTestData();
        updateTrainComboBox();
        bookingTablesInit();


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
                int id = Integer.parseInt(tfIdRoutes.getText());
                String from = tfFrom.getText();
                String to = tfTo.getText();

                if(selectedOption.equals("Add")) {
                    if(from.isEmpty() || to.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please type the source and destination for your journey!");
                        return;
                    }

                    TrainRoutes.getInstance().addStation(from);
                    TrainRoutes.getInstance().addStation(to);
                    Train train = TrainRoutes.getInstance().getTrainById(id);
                    if(train == null) {
                        JOptionPane.showMessageDialog(null, "There is no train with that ID!");
                        return;
                    }
                    TrainRoutes.getInstance().createConnection(from, to, train, "00:00", "00:00", 0.0);

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

                bookingTablesInit();
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
                int id = Integer.parseInt(tfIdTrains.getText());
                int nrOfSeats = Integer.parseInt(tfNrSeatsTrains.getText());
                int bookedSeats = Integer.parseInt(tfBookedSeatsTrains.getText());
                int delay = Integer.parseInt((tfDelayTrains.getText()));
                String operation = cbOperationTrains.getSelectedItem().toString();

                if(operation.equals("Add") || operation.equals("Modify")) {
                    Train train;
                    if(operation.equals("Add")) {
                        train = new Train();
                        train.setId(id);
                    }else {
                        train = TrainRoutes.getInstance().getTrainById(id);
                    }

                    train.setId(id);
                    train.setNrSeats(nrOfSeats);
                    train.setBookedSeats(bookedSeats);
                    train.setDelayMinutes(delay);

                    if(train.getDelayMinutes() != 0) {
                        train.delayNotification();
                    }

                    TrainRoutes.getInstance().addOrUpdateTrain(train);
                    JOptionPane.showMessageDialog(null, "Train has been added/modified successfully!");
                }else if(operation.equals("Delete")) {
                    TrainRoutes.getInstance().removeTrain(id);
                    JOptionPane.showMessageDialog(null, "Train has been deleted successfully!");
                }

                updateTrainComboBox();
            }
        });

        cbSelectTrain.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selectedOption = cbSelectTrain.getSelectedItem();
                if(selectedOption == null) {
                    return;
                }

                int id = Integer.parseInt(selectedOption.toString());
                String[] collumnNames = {"Departure Station", "Arrival Station", "Dep. Time", "Arr. Time", "Number of Seats", "Booked Seats", "Delay Minutes"};
                DefaultTableModel model = new DefaultTableModel(collumnNames, 0);

                List<Edge> allEdges = routingService.getAllEdges();

                for(Edge edge : allEdges) {
                    if(edge.getTrain().getId() == id) {
                        Object[] row = {
                                edge.getFrom().getName(),
                                edge.getTo().getName(),
                                edge.getDepartureTime(),
                                edge.getArrivalTime(),
                                edge.getTrain().getNrSeats(),
                                edge.getTrain().getBookedSeats(),
                                edge.getTrain().getDelayMinutes()
                        };
                        model.addRow(row);
                    }
                }
                tbBookings.setModel(model);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = tfUser.getText();
                char[] passChars = pfPassword.getPassword();
                String pass = new String(passChars);

                if(user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please type the username and password!");
                }else {
                    if(user.equalsIgnoreCase("Admin") && pass.equals("admin")) {
                        panelLogin.setVisible(false);
                        panelManagement.setVisible(true);
                        routeModel.setNewData(routingService.getAllEdges());
                        panel1.revalidate();
                        panel1.repaint();
                        JOptionPane.showMessageDialog(null, "Login successful!");
                    }else {
                        JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    }
                }
            }
        });

        btnSubmitBooking.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String departureTime = tfDepTimeBooking.getText();
                int numberOfTickets = Integer.parseInt(tfTicketNoBooking.getText());
                String mail = tfMail.getText();

                int rowFrom = tbFrom.getSelectedRow();
                int rowTo = tbTo.getSelectedRow();

                if(rowTo == -1 || rowFrom == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a station for both cities!");
                    return;
                }

                String fromStation = tbFrom.getModel().getValueAt(rowFrom, 0).toString();
                String toStation = tbTo.getModel().getValueAt(rowTo, 0).toString();

                if(fromStation.equals(toStation)) {
                    JOptionPane.showMessageDialog(null, "Please select different stations for departure and arrival!");
                    return;
                }

                Booking booking = new Booking();
                booking.submitBooking(fromStation, toStation, departureTime, numberOfTickets, mail);
            }
        });

    }
}
