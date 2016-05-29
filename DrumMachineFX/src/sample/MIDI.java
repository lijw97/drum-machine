package sample;

import javax.sound.midi.*;

public class MIDI
{
    public MIDI()
    {

    }

    public void play()
    {
        try
        {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, 44, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 44, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            sequencer.setSequence(sequence);
            sequencer.start();
        }
        catch (MidiUnavailableException e)
        {
            System.out.println("Failed to create the sequencer");
            e.printStackTrace();
            return;
        }
        catch (InvalidMidiDataException e)
        {
            System.out.println("Failed to create the sequence");
            e.printStackTrace();
        }
    }
}
