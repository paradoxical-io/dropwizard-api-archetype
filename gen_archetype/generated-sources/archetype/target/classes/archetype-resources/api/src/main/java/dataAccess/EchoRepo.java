#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dataAccess;

import ${package}.dataAccess.interfaces.DbRepo;

public class EchoRepo implements DbRepo {
    @Override public String echo(final String data) {
        return data;
    }
}
