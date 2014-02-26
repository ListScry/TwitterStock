API_KEY = "2a13913dda346761765020c1f66e34f8"

import unittest
from DatumBox import DatumBox, DatumBoxError


positive_tweet = """Also, @xDaniielle has understood she can't beat me playing GTA. So instead, she's grabbing a copy too. I have an amazing girlfriend :D"""

#The below tweet was not written by me, I would never say such a horrid thing.
negative_tweet = """Gah! I hate programming. Been pissing me off all day. Time to go sit on the sofa in a huff with a beer :("""

#Begin tests
datum_box = DatumBox(API_KEY)

result = datum_box.twitter_sentiment_analysis(negative_tweet)



print(result)