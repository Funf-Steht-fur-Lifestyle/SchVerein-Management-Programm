import sqlite3
from sqlite3 import Error

def main():

    #Datenbankfile hier eintragen. Pfad kann auch absolut angegeben werden.
    database = "TestDatabaseSchuetzenverein.db"

    # create a database connection
    conn = create_connection(database)
    with conn:
        print("1. Query task by priority:")
        select_Mitglied_by_id(conn, 1)

        print("2. Query all Mitglieder")
        select_all_Mitglieder(conn)

def create_connection(db_file):
    """ create a database connection to the SQLite database
        specified by the db_file
    :param db_file: database file
    :return: Connection object or None
    """

    conn = None
    try:
        conn = sqlite3.connect(db_file)
    except Error as e:
        print(e)

    return conn

def select_all_Mitglieder(conn):
    """
    Query all rows in the Mitglieder table
    :param conn: the Connection object
    :return:
    """
    cur = conn.cursor()
    #Query
    cur.execute("SELECT * FROM Mitglieder")

    rows = cur.fetchall()

    #Ausgabe/Weiterverarbeitung
    for row in rows:
        print(row)

def select_Mitglied_by_id(conn, id):
    """
    Query Mitglieder by id
    :param conn: the Connection object
    :param id:
    :return:
    """
    cur = conn.cursor()

    #Query
    cur.execute("SELECT * FROM Mitglieder WHERE id=?", (id,))
    #cur.execute("UPDATE Mitglieder SET name = \"Eric\" WHERE id=?;", (2,))

    rows = cur.fetchall()

    #Ausgabe/Weiterverarbeitung
    for row in rows:
        print(row)



if __name__ == '__main__':
    main()