package GUI;

import bestroute.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RouteTableModel extends AbstractTableModel {
    private final String[] columnNames = {"From", "To", "Departure", "Arrival", "Train ID"};
    private List<Edge> routes;

    public RouteTableModel(List<Edge> routes) {
        this.routes = routes;
    }

    public List<Edge> getRoutes() {
        return routes;
    }

    public Edge getEdgeAt(int row) {
        return routes.get(row);
    }

    public void setNewData(List<Edge> newEdges) {
        this.routes = newEdges;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return routes.size(); }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public String getColumnName(int col) { return columnNames[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Edge edge = routes.get(row);
        return switch (col) {
            case 0 -> edge.getFrom().getName();
            case 1 -> edge.getTo().getName();
            case 2 -> edge.getDepartureTime();
            case 3 -> edge.getArrivalTime();
            case 4 -> edge.getTrain().getId();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 2 || col == 3 || col == 4;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Edge edge = routes.get(row);
        String newValue = (String) value;

        if (col == 2) {
            edge.setDepartureTime(newValue);
        }
        if (col == 3) {
            edge.setArrivalTime(newValue);
        }
        if(col == 4) {
            edge.getTrain().setId(Integer.parseInt(newValue));
        }

        fireTableCellUpdated(row, col);
    }
}