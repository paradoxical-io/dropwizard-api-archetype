#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dataAccess.interfaces;

public interface DbRepo {
    String echo(String username);
}
