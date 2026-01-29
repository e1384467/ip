import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import jerry.exceptions.JerryException;
import jerry.parser.Parser;
import jerry.task.Deadline;
import jerry.task.Event;
import jerry.task.ToDo;

public class ParserTest {

    @Test
    public void parseTodo_success() throws JerryException {
        assertEquals(new ToDo("Sleep").toString(), Parser.parseTodo("Sleep").toString());
        assertEquals(new ToDo("Eat").toString(), Parser.parseTodo("Eat").toString());
    }

    @Test
    public void parseTodo_emptyUserInput_missingArgumentExceptionThrown() {
        try {
            assertEquals(new ToDo("").toString(), Parser.parseTodo("").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                    + "todo <your task goes here>\n", e.getMessage());
        }
    }

    @Test
    public void parseTodo_userInputContainsPipe_wrongArgumentExceptionThrown() {
        try {
            assertEquals(new ToDo("eat |").toString(), Parser.parseTodo("eat |").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "Character '|' is not allowed in your task description.\n", e.getMessage());
        }
    }

    @Test
    public void parseDeadline_success() throws JerryException {
        assertEquals(new Deadline("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseDeadline("Sleep /by 06062002 0540").toString());
        assertEquals(new Deadline("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseDeadline("Sleep /By 06062002 0540").toString());
        assertEquals(new Deadline("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseDeadline("Sleep /bY 06062002 0540").toString());
        assertEquals(new Deadline("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseDeadline("Sleep /BY 06062002 0540").toString());
    }

    @Test
    public void parseDeadline_userInputContainsPipe_wrongArgumentExceptionThrown() {
        try {
            assertEquals(new Deadline("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseDeadline("Sleep | /by 06062002 0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "Character '|' is not allowed in your input.\n", e.getMessage());
        }
    }

    @Test
    public void parseDeadline_emptyTaskField_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Deadline("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseDeadline("/by 06062002 1300").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                    + "deadline <your task goes here> /by <ddmmyyyy hhmm (24-hour clock)>\n", e.getMessage());
        }
    }

    @Test
    public void parseDeadline_emptyByField_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Deadline("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseDeadline("Sleep").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                    + "deadline <your task goes here> /by <ddmmyyyy hhmm (24-hour clock)>\n", e.getMessage());
        }
    }


    @Test
    public void parseDeadline_emptyUserInput_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Deadline("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseDeadline("").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                    + "deadline <your task goes here> /by <ddmmyyyy hhmm (24-hour clock)>\n", e.getMessage());
        }
    }

    @Test
    public void parseDeadline_wrongDateTimeFormate_wrongArgumentExceptionThrown() {
        try {
            assertEquals(new Deadline("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseDeadline("Sleep /by 06062002-0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "There is issue with your date and time format.\n"
                    + "Try: ddmmyyyy hhmm (24-hour clock)\n"
                    + "E.g. 06062002 0530\n", e.getMessage());
        }
    }

    @Test
    public void parseEvent_success() throws JerryException {
        assertEquals(new Event("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                        LocalDateTime.parse("07062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseEvent("Sleep /from 06062002 0540 /to 07062002 0540").toString());
        assertEquals(new Event("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                        LocalDateTime.parse("07062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseEvent("Sleep /FroM 06062002 0540 /tO 07062002 0540").toString());

    }

    @Test
    public void parseEvent_userInputContainsPipe_wrongArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("Sleep | /from 06062002 0540 /to 07062002 0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "Character '|' is not allowed in your input.\n", e.getMessage());
        }
    }

    @Test
    public void parseEvent_emptyTaskField_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("/from 06062002 0540 /to 07062002 0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                            + "event <your task goes here> "
                            + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n",
                    e.getMessage());
        }
    }

    @Test
    public void parseEvent_emptyFromField_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("Sleep /to 07062002 0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                            + "event <your task goes here> "
                            + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n",
                    e.getMessage());
        }
    }

    @Test
    public void parseEvent_emptyToField_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("Sleep /from 06062002 0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                            + "event <your task goes here> "
                            + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n",
                    e.getMessage());
        }
    }

    @Test
    public void parseEvent_emptyUserInput_missingArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                            + "event <your task goes here> "
                            + "/from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>\n",
                    e.getMessage());
        }
    }

    @Test
    public void parseEvent_invalidTimeFrame_wrongArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("Sleep /from 07062002 0540 /to 06062002 0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "Invalid Time Range\n"
                    + "Your start time must be before end time\n", e.getMessage());
        }
    }

    @Test
    public void parseEvent_wrongDateTimeFormate_wrongArgumentExceptionThrown() {
        try {
            assertEquals(new Event("Sleep",
                            LocalDateTime.parse("06062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm")),
                            LocalDateTime.parse("07062002 0540",
                                    DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                    Parser.parseEvent("Sleep /from 06062002-0540 /to 07062002T0540").toString());
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "There is issue with your date and time format.\n"
                    + "Try: ddmmyyyy hhmm (24-hour clock)\n"
                    + "E.g. 06062002 0530\n", e.getMessage());
        }
    }

    @Test
    public void getArrayIndex_sucess() throws JerryException {
        assertEquals(-1, Parser.getArrayIndex("Mark 0".split("\\s+")));
        assertEquals(0, Parser.getArrayIndex("Mark 1".split("\\s+")));
        assertEquals(1, Parser.getArrayIndex("Mark 2".split("\\s+")));
        assertEquals(2, Parser.getArrayIndex("Mark 3".split("\\s+")));
    }

    @Test
    public void getArrayIndex_notANumber_wrongArgumentExceptionThrown() {
        try {
            assertEquals(0, Parser.getArrayIndex("Mark task1".split("\\s+")));
            fail();
        } catch (JerryException e) {
            assertEquals("Wrong Argument >:( !!!!\n"
                    + "THIS IS NOT A NUMBER\n", e.getMessage());
        }
    }

    @Test
    public void getArrayIndex_missingUserInputs_missingArgumentExceptionThrown() {
        try {
            assertEquals(0, Parser.getArrayIndex("".split("\\s+")));
            fail();
        } catch (JerryException e) {
            assertEquals("Missing Argument >:( !!!! Please try:\n"
                    + "Mark <your task index from list>\n"
                    + "or\n"
                    + "Unmark <your task index from list>\n"
                    + "or\n"
                    + "Delete <your task index from list>\n", e.getMessage());
        }
    }
}
