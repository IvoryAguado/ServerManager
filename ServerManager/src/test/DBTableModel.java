package test;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.table.AbstractTableModel;
import java.sql.Types;

/**
 *
 * @author Luismi
 */
public class DBTableModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	CachedRowSet rs;
    int nFilas, nColumnas;
    ResultSetMetaData meta;

    public DBTableModel(String sql) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            RowSetFactory rsf = RowSetProvider.newFactory();
            rs = rsf.createCachedRowSet();

            rs.setUrl("jdbc:oracle:thin:NORTHWIND/1@localhost:1521:XE");
            rs.setConcurrency(CachedRowSet.CONCUR_UPDATABLE);
            rs.setCommand(sql);
            rs.execute();

            meta = rs.getMetaData();
            nColumnas = meta.getColumnCount();

            nFilas = 0;
            rs.beforeFirst();
            while (rs.next()) {
                nFilas++;
            }
            rs.beforeFirst();

        } catch (SQLException e) {
            System.err.println(e.toString());
        }

    }

    @Override
    public int getRowCount() {
        return nFilas;
    }

    @Override
    public int getColumnCount() {
        return nColumnas;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            //Le sumamos 1 porque JTable comienza a contar en 0 y 
            //RowSet en 1
            rs.absolute(rowIndex + 1);
            Object o = rs.getObject(columnIndex + 1);
            if (o == null) {
                return null;
            } else {
                return o;
            }
        } catch (SQLException ex) {
            return ex.toString();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //Si queremos modificar este comportamiento, podemos extender la clase
        return true;
    }

    @Override
    public String getColumnName(int column) {
        String name = null;
        try {
            name = meta.getColumnName(column + 1);
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return name;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            rs.absolute(rowIndex + 1);
            rs.updateObject(columnIndex + 1, aValue);
            rs.updateRow();
            fireTableDataChanged();

        } catch (SQLException ex) {
            Logger.getLogger(DBTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        int type = 0;
        try {
            type = meta.getColumnType(columnIndex + 1);
        } catch (SQLException ex) {
            Logger.getLogger(DBTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return correspondenciaTipoOracleTipoJava(type);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBTableModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /*
     * Este método es una forma genérica de devolver el tipo de dato java
     * que más se parezca a su correspondiente tipo de dato SQL
     */
    private Class<?> correspondenciaTipoOracleTipoJava(int type) throws ClassNotFoundException {
        String cname = null;

        switch (type) {
            case Types.BIT: {
                cname = "java.lang.Boolean";
                break;
            }
            case Types.TINYINT: {
                cname = "java.lang.Byte";
                break;
            }
            case Types.SMALLINT: {
                cname = "java.lang.Short";
                break;
            }
            case Types.INTEGER: {
                cname = "java.lang.Integer";
                break;
            }
            case Types.BIGINT: {
                cname = "java.lang.Long";
                break;
            }
            case Types.FLOAT:
            case Types.REAL: {
                cname = "java.lang.Float";
                break;
            }
            case Types.DOUBLE: {
                cname = "java.lang.Double";
                break;
            }
            case Types.NUMERIC: {
                cname = "java.lang.Number";
                break;
            }
            case Types.DECIMAL: {
                cname = "java.math.BigDecimal";
                break;
            }
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR: {
                cname = "java.lang.String";
                break;
            }
            case Types.DATE: {
                cname = "java.sql.Date";
                break;
            }
            case Types.TIME: {
                cname = "java.sql.Time";
                break;
            }
            case Types.TIMESTAMP: {
                cname = "java.sql.Timestamp";
                break;
            }
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY: {
                cname = "byte[]";
                break;
            }
            case Types.OTHER:
            case Types.JAVA_OBJECT: {
                cname = "java.lang.Object";
                break;
            }
            case Types.CLOB: {
                cname = "java.sql.Clob";
                break;
            }
            case Types.BLOB: {
                cname = "java.ssql.Blob";
                break;
            }
            case Types.REF: {
                cname = "java.sql.Ref";
                break;
            }
            case Types.STRUCT: {
                cname = "java.sql.Struct";
                break;
            }


        }

        return Class.forName(cname);


    }

}