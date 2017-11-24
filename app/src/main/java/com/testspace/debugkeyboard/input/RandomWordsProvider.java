package com.testspace.debugkeyboard.input;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class RandomWordsProvider {
    private Phrase currentPhrase;

    @Inject
    RandomWordsProvider() {}

    String getRandomWord() {
        return getRandomPhrase().getNextWord();
    }

    private Phrase getRandomPhrase() {
        if (currentPhrase == null || currentPhrase.isEnded()) {
            currentPhrase = pickRandom();
        }
        return currentPhrase;
    }

    private Phrase pickRandom() {
        return new Phrase(imdbQuotes[(int) ((imdbQuotes.length - 1) * Math.random())]);
    }

    private class Phrase {
        private final String[] words;
        int wordIndex = -1;

        Phrase(String wholePhrase) {
            words = wholePhrase.split(" ");
        }

        String getNextWord() {
            wordIndex++;
            if (isEnded()) {
                return "";
            }
            return words[wordIndex];
        }

        boolean isEnded() {
            return !(wordIndex < words.length);
        }
    }

    private final String[] imdbQuotes = new String[] {
            "A man who doesn't spend time with his family can never be a real man",
            "I'm as mad as hell and I'm not gonna take this anymore!",
            "Champagne for my real friends, real pain for my sham friends!",
            "If you think of yourself as funny, you become tragic.",
            "As far back as I can remember, I always wanted to be a gangster",
            "What we've got here is ... failure to communicate",
            "You don't make up for your sins in church. You do it in the streets",
            "I was a better man with you, as a woman... than I ever was with a woman, as a man.",
            "Madness! Madness!",
            "After fifteen minutes I wanted to marry her, and after half an hour I completely gave up the idea of stealing her purse.",
            "Not only are you rich, but you want to be loved as if you are poor.",
            "Is it safe?",
            "You get some gorgeous eyes, d'you know...",
            "I know who I am!",
            "Paris is small for those who share so great a passion as ours.",
            "You were a tomato! A tomato doesn't have a logic, a tomato can't move!",
            "Don Corleone",
            "There is something very important we need to do as soon as possible.",
            "I love you. I've loved you since the first moment I saw you. I guess maybe I've even loved you before I saw you.",
            "When you side with a man, you stay with him. And if you can't do that, you're like some animal, you're finished.",
            "I want that you tell me... was she feeble-minded? My mother! Was she feeble-minded? Was she?",
            "You're out of order! You're out of order! The whole trial is out of order! They're out of order!",
            "Remember when you was a kid and you would spend the whole year waiting for summer vacation and when it finally came it would fly by just like that? It's funny, Jimmy, life has a way of flying by faster than any old summer vacation really fucking does.",
            "Time. Time. What is time? Swiss manufacture it. French hoard it. Italians squander it. Americans say it is money. Hindus say it does not exist. Do you know what I say? I say time is a crook.",
            "Eddie, you're a born loser",
            "Forget about it",
            "Once you start compromising your thoughts, you're a candidate for mediocrity",
            "You sho' is ugly!",
            "Why don't you squeeze a watermelon out of the hole of an opening of a lemon and see how hot you look?",
            "It ain't about how hard ya hit. It's about how hard you can get it and keep moving forward. How much you can take and keep moving forward. That's how winning is done!",
            "I'll remember you, honey. You're the one that got away",
            "I'm Godzilla! You are JAPAN!",
            "Merciful death, how you love your precious guilt",
            "In Switzerland they had brotherly love - they had 500 years of democracy and peace, and what did that produce? The cuckoo clock.",
            "You are the greatest lover I've ever had.",
            "Elaaaaaaine! Elaaaaaaine!",
            "I know, you did send me back to the future, but I'm back -- I'm back FROM the future ...",
            "You want me to hold the chicken, huh?",
            "Granny Snuff, ever been snuffed out?",
            "Don't ever take sides with anyone against the Family again. Ever.",
            "The greatest trick the devil ever pulled was convincing the world he didn't exist ... and like that, he's gone",
            "Hey, Solomon, Sliman... Are you guys cousins?",
            "At least you'll never be a vegetable - even artichokes have hearts",
            "Once I buried two Arabs in a wall over there... Facing Mecca, of course.",
            "You talkin' to me?",
            "You can have my answer now, if you like. My final offer is this: nothing. Not even the fee for the gaming license, which I would appreciate if you would put up personally.",
            "Solomon, you're Jewish?",
            "Wait. You dropped your phony dog poo",
            "It's easier to die for a woman than to find one worth dying for.",
            "Finance is a gun. Politics is knowing when to pull the trigger",
            "We're judged by the company we keep.",
            "Everybody be cool, this is a robbery!",
            "Just one more thing Sofie. Is she aware her daughter is alive?",
            "Hello. My name is Inigo Montoya. You killed my father. Prepare to die",
            "These go to eleven",
            "I said 'Bizarre bizarre'? How bizarre. Why would I have said, 'Bizarre, bizarre'?",
            "Hey, amigo! You know you have a face beautiful enough to be worth $2000?",
            "Yo Adrian, I did it!",
            "Noodles, I slipped",
            "You call that a knife? This is a knife!",
            "I see dead people",
            "See you at the party, Richter!",
            "All right, Mr. De Mille, I'm ready for my close-up",
            "Fasten your seatbelts, it's going to be a bumpy night",
            "I wish you was a wishing well, so I could tie a bucket to you and sink you",
            "Play 'Misty' for me",
            "A floor-rag... How beautiful! I'm so happy!",
            "Why am I Mr. Pink?",
            "Things would be much as they are now. Perhaps. Except maybe I wouldn't have named our son Elvis.",
            "You know...hostility is like a psychic boomerang.",
            "I can feel that tonight ... I'll make it with her",
            "Hasta la vista, baby",
            "Favor gonna kill you faster than a bullet",
            "I wanted to destroy something beautiful",
            "Now let me correct you on a couple of things, OK? Aristotle was not Belgian. The central message of Buddhism is not ",
            "They do taste home-made! Hand-made, and rolled under the armpits.",
            "Remember who you are",
            "But this ship can't sink!",
            "Bingo!",
            "Look Bernard... I think you and I have the same problem: the point is that we cannot really rely on our looks, especially you. So let me give you a piece of advice: forget you don't stand a chance, and just go for it! Who knows, it might work out of a missunderstanding!",
            "I hate to speak ill of people. Such a nice person indeed!",
            "Nuskat Muss! Nuskat Muss, Herr Muller!",
            "Atmosphere? Atmosphere? Do I look like an atmosphere?",
            "Have you ever seen a grown man naked?",
            "We're both part of the same hypocrisy, senator, but never think it applies to my family.",
            "They was giving me ten thousand watts a day, you know, and I'm hot to trot! The next woman takes me on's gonna light up like a pinball machine and pay off in silver dollars!",
            "You owe me MONEY!",
            "After all, tomorrow is another day",
            "A man's got to know his limitations",
            "Adriaaaaaaaaaaan!",
            "Well, there wasn't enough time, Michael. There wasn't enough time",
            "Party's over!",
            "Run Forrest, Run!",
            "Althoooooough... I HAVE developed a greater appreciation for the female version of the human anatomy... ARROOOOO!",
            "Let me be frank!",
            "I'm more man than you'll ever be and more woman than you'll ever get",
            "Well, nobody's perfect",
            "Wait a minute. I'm the leader, I say when it's the end ... It's the end."
    };
}
